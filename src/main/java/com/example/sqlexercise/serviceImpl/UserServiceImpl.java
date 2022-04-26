package com.example.sqlexercise.serviceImpl;

import com.example.sqlexercise.data.CodeMapper;
import com.example.sqlexercise.data.UserMapper;
import com.example.sqlexercise.lib.Passhash;
import com.example.sqlexercise.po.Cache;
import com.example.sqlexercise.po.User;
import com.example.sqlexercise.service.UserService;
import com.example.sqlexercise.vo.ResponseVO;
import com.example.sqlexercise.vo.SignVO;
import com.example.sqlexercise.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    CodeMapper codeMapper;

    @Override
    public ResponseVO signUp(UserVO userVO){
        //验证码校验
        String email = userVO.getEmail();
        Cache cache = codeMapper.getCacheByEmail(email);
        String rightCode = cache.getCode();
        if(cache.getExpiryAt().before(new Date())){
            return ResponseVO.failure("验证码已过期，请重新发送");
        }
        if(!rightCode.equals(userVO.getCode())){
            return ResponseVO.failure("验证码错误！");
        }
        if (!userVO.getPassword().equals(userVO.getPasswordComfirmation())) {
            return ResponseVO.failure("两次输入的密码不一致！");
        }
        if (userMapper.findOneByEmail(email)!=null){
            return ResponseVO.failure("用户已存在！");
        }
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        user.setId(UUID.randomUUID().toString());
        user.setPasshash(Passhash.getPasshash(userVO.getPassword()));
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user.setRole("user");
        userMapper.create(user);

        return ResponseVO.success("注册成功");
    }

    @Override
    public ResponseVO signIn(SignVO signVO){
        User user = userMapper.findOneByEmail(signVO.getEmail());
        boolean isMatch = Passhash.comparePassword(signVO.getPassword(),user.getPasshash());
        if(isMatch){
            return ResponseVO.success(user);
        }else{
            return ResponseVO.failure("邮箱或密码错误");
        }
    }
}
