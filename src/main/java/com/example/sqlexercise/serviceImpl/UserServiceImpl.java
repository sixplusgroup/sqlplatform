package com.example.sqlexercise.serviceImpl;

import com.example.sqlexercise.data.*;
import com.example.sqlexercise.lib.PasswordHash;
import com.example.sqlexercise.lib.Constants;
import com.example.sqlexercise.po.PassRecord;
import com.example.sqlexercise.po.User;
import com.example.sqlexercise.service.UserService;
import com.example.sqlexercise.vo.ResponseVO;
import com.example.sqlexercise.vo.SignVO;
import com.example.sqlexercise.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    private PassRecordMapper passRecordMapper;
    private QuestionStateMapper questionStateMapper;
    private BatchMapper batchMapper;
    private SubQuestionMapper subQuestionMapper;

    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, PassRecordMapper passRecordMapper, QuestionStateMapper questionStateMapper, BatchMapper batchMapper, SubQuestionMapper subQuestionMapper, StringRedisTemplate stringRedisTemplate) {
        this.userMapper = userMapper;
        this.passRecordMapper = passRecordMapper;
        this.questionStateMapper = questionStateMapper;
        this.batchMapper = batchMapper;
        this.subQuestionMapper = subQuestionMapper;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public ResponseVO signUp(UserVO userVO) {
        //验证码校验
        String email = userVO.getEmail();
        String rightCode = stringRedisTemplate.opsForValue().get(Constants.RedisKey.SIGN_UP_CODE_KEY_PREFIX + email);
        if (rightCode == null) {
            return ResponseVO.failure("验证码已过期，请重新发送");
        }
        if (!rightCode.equals(userVO.getCode())) {
            return ResponseVO.failure("验证码错误！");
        }
        if (!userVO.getPassword().equals(userVO.getPasswordConfirmation())) {
            return ResponseVO.failure("两次输入的密码不一致！");
        }
        if (userMapper.selectByEmail(email) != null) {
            return ResponseVO.failure("用户已存在！");
        }
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        user.setId(UUID.randomUUID().toString());
        user.setPasswordHash(PasswordHash.getPasswordHash(userVO.getPassword()));
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user.setRole("user");
        userMapper.insert(user);

        return ResponseVO.success("注册成功");
    }

    @Override
    public ResponseVO signIn(SignVO signVO) {
        User user = userMapper.selectByEmail(signVO.getEmail());
        if (user == null) {
            return ResponseVO.failure("该邮箱未注册");
        }
        boolean isMatch = PasswordHash.comparePassword(signVO.getPassword(), user.getPasswordHash());
        if (isMatch) {
            return ResponseVO.success(user);
        } else {
            return ResponseVO.failure("邮箱或密码错误");
        }
    }

    @Override
    public List<PassRecord> getRecords(String userId) {
        return passRecordMapper.selectByUserId(userId);
    }

    @Override
    public UserVO getUserInfo(String userId) {
        User user = userMapper.selectById(userId);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public ResponseVO modifyInfo(UserVO userVO) {
        // 创建PO对象
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        String password = userVO.getPassword();
        if (password != null && !password.equals("")) {
            user.setPasswordHash(PasswordHash.getPasswordHash(password));
        }
        String name = userVO.getName();
        if (name != null && !name.equals("")) {
            user.setName(name);
        }
        user.setUpdatedAt(new Date());

        // 执行数据库更新操作
        int res = userMapper.update(user);
        if (res == 1) {
            return ResponseVO.success("修改成功");
        } else {
            return ResponseVO.failure("修改失败");
        }
    }

    /**
     * 获取某一用户收藏的题目列表
     */
    @Override
    public Object getStars(String userId) {
        List<Map<String, Object>> res = questionStateMapper.selectStars(userId);
        return res;
    }

    /**
     * 获取用户做题统计数据，包括已通过题目数、提交未通过题目数、未开始题目数、提交总次数、提交通过次数、提交通过率
     */
    @Override
    public Object getStatistic(String userId) {
        Map<String, Object> res = new HashMap<>();

        // 查subQuestion总数
        int totalNumOfSubQuestion = subQuestionMapper.countTotal();
        res.put("totalNumOfSubQuestion", totalNumOfSubQuestion);

        // 查该用户已通过题目数
        int passedQuestionNum = questionStateMapper.selectPassedByUserId(userId);
        res.put("passedQuestionNum", passedQuestionNum);

        // 查提交未通过题目数
        int submittedButNotPassQuestionNum = questionStateMapper.selectSubmittedButNotPassByUserId(userId);
        res.put("submittedButNotPassQuestionNum", submittedButNotPassQuestionNum);

        // 计算未开始题目数
        int notStartedQuestionNum = totalNumOfSubQuestion - passedQuestionNum - submittedButNotPassQuestionNum;
        res.put("notStartedQuestionNum", notStartedQuestionNum);

        // 查用户提交总次数
        int submitTimes = batchMapper.selectSubmitTimesByUserId(userId);
        res.put("submitTimes", submitTimes);

        // 查用户提交通过次数
        int passTimes = passRecordMapper.selectPassTimesByUserId(userId);
        res.put("passTimes", passTimes);

        // 计算用户提交通过率
        String passRate = String.format("%.2f", (double) passTimes / (double) submitTimes * 100) + '%';
        res.put("passRate", passRate);
        
        return res;
    }

    /**
     * 获取用户最近 n 条提交记录
     */
    @Override
    public Object getRecentSubmits(String userId, int n) {
        List<Object> res = batchMapper.selectRecentSubmits(userId, n);
        return res;
    }

}
