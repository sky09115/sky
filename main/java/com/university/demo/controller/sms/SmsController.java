package com.university.demo.controller.sms;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.university.demo.dao.sms.SmsCodeDao;
import com.university.demo.entity.User;
import com.university.demo.entity.sms.SmsCode;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.service.UserService;
import com.university.demo.util.Sms.RandomUtil;
import com.university.demo.util.Sms.SendSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author redcomet
 * @since  09-20
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
        smsCode.setExpire_time(expireTime);

        int b = dao.insert(smsCode);

        if (b>0) {
            SendSms.getMessageStatus(phone, code);
            return ServerResponse.ofSuccess("发送成功", smsCode);
        }
        return ServerResponse.ofError("发送失败!");
    }

    @PostMapping("/sendSms")
    public ServerResponse sendSms(@RequestBody SmsCode smsCode) throws IOException {
        String code = RandomUtil.generateDigitalString(6);
        String phone = smsCode.getPhone();
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + 1 * 1 * 3600 * 1000);//过期时间 1 小时
        smsCode.setCode(code);
        smsCode.setExpire_time(expireTime);

        int b = dao.insert(smsCode);

        if (b>0) {
            Map<String, Object> map = new HashMap();
//            map.put("msg","发送成功");
            map.put("code", code);
            SendSms.getMessageStatus(phone, code);
            return ServerResponse.ofSuccess("发送成功", map);
        }
        return ServerResponse.ofError("发送失败!");
    }

    /**
     * 根据新的前端做的修改密码新接口，覆盖了原方法
     * @param uid
     * @param smsCode
     * @return
     * @throws IOException
     */
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

