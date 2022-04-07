package com.example.sqlexercise.serviceImpl;

import com.example.sqlexercise.data.CodeMapper;
import com.example.sqlexercise.po.Cache;
import com.example.sqlexercise.service.MessageCodeService;
import com.example.sqlexercise.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@Service
public class MessageCodeServiceImpl implements MessageCodeService {

    @Autowired
    CodeMapper codeMapper;

    @Override
    public ResponseVO sendMessageCode(String email, String code){

        Properties properties = new Properties();
        //设置发送邮件的基本参数
        properties.put("mail.transport.protocol", "smtp");//协议
        properties.put("mail.smtp.host", "smtp.qq.com");//邮件服务器地址
        properties.put("mail.smtp.auth", "true");//需要请求认证
        properties.put("mail.smtp.port", "587");//端口号
        //SSL 安全认证
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        properties.put("mail.smtp.socketFactory.port", "465");
        //创建会话对象
        Session session = Session.getInstance(properties);
        session.setDebug(true);//可以查看详细的发送Log
        //创建一封邮件
        try {
            MimeMessage message = new MimeMessage(session);
            //From
            message.setFrom(new InternetAddress("wenbing.he@qq.com", "sqlexercise", "UTF-8"));
            //To
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email, "sqlexercise 新用户", "UTF-8"));
            //主题
            message.setSubject("SQL Exercise 注册验证码", "UTF-8");
            //正文
            message.setContent("\n\n" +
                    "            <p>欢迎来到 SQL Exercise</p>\n" +
                    "            <p>您的网站账户注册验证码是：</p>\n" +
                    "        <span style=\"font-size: 24px; color: red\">" + code + "</span>", "text/html;charset=UTF-8");
            message.setSentDate(new Date());
            Transport transport = session.getTransport();
            transport.connect("wenbing.he@qq.com", "rzljkrkpwkxqbfbd");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return ResponseVO.success("发送成功");
    }

    @Override
    public String generateMessageCode(String email){
        StringBuilder newCode = new StringBuilder();
        String all = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (int i = 0; i < 6; i++) {
            int num = (int) (Math.random() * 82);
            newCode.append(all.charAt(num));
        }
        Cache cache = new Cache();
        cache.setCode(newCode.toString());
        Date now = new Date();
        Date expiryDate = new Date(now.getTime()+5*60*1000);
        cache.setCreatedAt(now);
        cache.setExpiryDate(expiryDate);
        if(codeMapper.getCacheByEmail(email)!=null){
            codeMapper.update(cache);
        }else {
            codeMapper.create(cache);
        }
        return newCode.toString();
    }
}
