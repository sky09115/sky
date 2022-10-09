/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package com.university.demo.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.university.demo.anotation.SysLog;
import com.university.demo.controller.api.service.ApiLoginService;
import com.university.demo.controller.api.util.Result;
import com.university.demo.controller.api.util.ResultGenerator;
import com.university.demo.dao.SmsCodeDao;
import com.university.demo.entity.SmsCode;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.entity.system.ServiceResultEnum;
import com.university.demo.entity.system.SysConstant;
import com.university.demo.anotation.TokenToUser;
import com.university.demo.entity.User;
import com.university.demo.entity.request.UserLoginRequest;
import com.university.demo.entity.response.UserVo;
import com.university.demo.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserAPI {

    private static final Logger logger = LoggerFactory.getLogger(UserAPI.class);

    @Autowired
    private ApiLoginService apiLoginService;

    @Autowired
    private UserService userService;

    @Autowired
    private SmsCodeDao dao;

    @PostMapping("/user/login")
    @SysLog(value= SysConstant.APP_LOGIN)
    public Result<String> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        String loginResult = apiLoginService.login(userLoginRequest.getUsername(), userLoginRequest.getPassword());
        logger.info("login api,loginName={},loginResult={}", userLoginRequest.getUsername(), loginResult);

        //登录成功
        if (!StringUtils.isEmpty(loginResult) && loginResult.length() == SysConstant.TOKEN_LENGTH) {
            Result result = ResultGenerator.genSuccessResult();
            result.setData(loginResult);
            return result;
        }
        //登录失败
        return ResultGenerator.genFailResult(loginResult);
    }


    @PostMapping("/user/logout")
    @ApiOperation(value = "登出接口", notes = "清除token")
    public Result<String> logout(@TokenToUser User loginMallUser) {
        Boolean logoutResult = apiLoginService.logout(Long.valueOf(loginMallUser.getId()));

        logger.info("logout api,loginMallUser={}", loginMallUser.getId());

        //登出成功
        if (logoutResult) {
            return ResultGenerator.genSuccessResult();
        }
        //登出失败
        return ResultGenerator.genFailResult("logout error");
    }

    @PostMapping("/user/register/{code}")
    @ApiOperation(value = "用户注册", notes = "")
    public Result register(@RequestBody @Valid User mallUserRegisterParam, @PathVariable String code) {
        //验证下验证码
        QueryWrapper<SmsCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("expire_time");
        queryWrapper.eq("phone", mallUserRegisterParam.getPhone());
        SmsCode code2 = dao.selectList(queryWrapper).get(0);
        if(!code2.getCode().equals(code))
            return ResultGenerator.genFailResult("验证码不符!");

        String registerResult = apiLoginService.register(mallUserRegisterParam);
        logger.info("register api,loginName={},loginResult={},code={}", mallUserRegisterParam.getUsername(), registerResult,code);
        //注册成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(registerResult)) {
            // 把电话也邮箱也更新一下
            return ResultGenerator.genSuccessResult();
        }
        //注册失败
        return ResultGenerator.genFailResult(registerResult);
    }

    @PutMapping("/user/info")
    @ApiOperation(value = "修改用户信息", notes = "")
    public Result updateInfo(@RequestBody @ApiParam("用户信息") User mallUserUpdateParam, @TokenToUser User loginMallUser) {
        Boolean flag = apiLoginService.updateUserInfo(mallUserUpdateParam, loginMallUser.getId());
        if (flag) {
            //返回成功
            Result result = ResultGenerator.genSuccessResult();
            return result;
        } else {
            //返回失败
            Result result = ResultGenerator.genFailResult("修改失败");
            return result;
        }
    }

    @GetMapping("/user/info")
    @ApiOperation(value = "获取用户信息", notes = "")
    public Result<UserVo> getUserDetail(@TokenToUser User user) {
        UserVo mallUserVO = new UserVo(); //可以根据登录信息去关联出更多的user信息，所以用vo对象
        BeanUtils.copyProperties(user, mallUserVO);
        return ResultGenerator.genSuccessResult(mallUserVO);
    }

    @GetMapping("/user/info/{id}")
    public ServerResponse getUserByUid(@PathVariable("id") Integer id) {
        return ServerResponse.ofSuccess(userService.getById(id));
    }

    //扣费
    @PostMapping("/user/xiaofei")
    public ServerResponse xiaofei(@RequestBody User user) {
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("id", user.getId());
        User user2 = userService.getOne(wrapper);
        Double bal = user2.getBal() - user.getBal();
        if(bal<0){
            return ServerResponse.ofError("余额不足", user);
        }
        user2.setBal(bal);
        boolean b = userService.saveOrUpdate(user2);
        if (b) {
            return ServerResponse.ofSuccess("更新成功", user);
        }
        return ServerResponse.ofError("更新成功!");
    }
}
