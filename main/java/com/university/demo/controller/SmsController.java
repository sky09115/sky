package com.university.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.university.demo.entity.system.ServerResponse;

import com.university.demo.dao.SmsCodeDao;
import com.university.demo.entity.SmsCode;
import com.university.demo.entity.User;
import com.university.demo.service.UserService;
import com.university.demo.util.Sms.RandomUtil;
import com.university.demo.util.Sms.SendSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

/**
 * @author redcomet
 * @since 2021-09-20
 */
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private SmsCodeDao dao;

    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public ServerResponse sendMsg(@RequestBody SmsCode smsCode) throws IOException {
        String code = RandomUtil.generateDigitalString(6);
        String phone = smsCode.getPhone();
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + 1 * 1 * 3600 * 1000);//过期时间 1 小时
        smsCode.setCode(code);
        smsCode.setExpireTime(expireTime);

        int b = dao.insert(smsCode);

        if (b>0) {
            SendSms.getMessageStatus(phone, code);
            return ServerResponse.ofSuccess("发送成功", smsCode);
        }
        return ServerResponse.ofError("发送失败!");
    }

    @PostMapping("/modifyPass/{uid}")
    public ServerResponse modifyPass(@PathVariable("uid") Integer uid,@RequestBody SmsCode smsCode) throws IOException {
        try {
            QueryWrapper<SmsCode> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("expire_time");
            queryWrapper.eq("phone", smsCode.getPhone());
            SmsCode code = dao.selectList(queryWrapper).get(0);
            if(!code.getCode().equals(smsCode.getCode()))
                return ServerResponse.ofError("验证码不符!");

        }catch (Exception e){
            return ServerResponse.ofError("验证码校验失败!");
        }

        User user = userService.getById(uid);
        user.setPassword(smsCode.getNote());

        Boolean b = userService.updateById(user);

        if (b) {
            return ServerResponse.ofSuccess("修改密码成功");
        }
        return ServerResponse.ofError("修改密码失败!");
    }
}

