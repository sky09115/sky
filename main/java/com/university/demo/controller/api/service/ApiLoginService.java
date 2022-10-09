package com.university.demo.controller.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.university.demo.controller.api.util.NumberUtil;
import com.university.demo.controller.api.util.SystemUtil;
import com.university.demo.exception.GHException;
import com.university.demo.entity.system.ServiceResultEnum;
import com.university.demo.entity.system.SysConstant;
import com.university.demo.dao.UserDao;
import com.university.demo.dao.UserTokenDao;
import com.university.demo.entity.User;
import com.university.demo.entity.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class ApiLoginService {

    @Autowired
    private UserDao dao;

    @Autowired
    private UserTokenDao userTokenDao;

    public String login(String username, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("password", password);
        User user = dao.selectOne(wrapper);
        if (user != null) {
            //登录后即执行修改token的操作
            String token = getNewToken(System.currentTimeMillis() + "", user.getId());
            UserToken mallUserToken = userTokenDao.selectByUserId(user.getId());
            //当前时间
            Date now = new Date();
            //过期时间
            Date expireTime = new Date(now.getTime() + 2 * 24 * 3600 * 1000);//过期时间 48 小时
            if (mallUserToken == null) {
                mallUserToken = new UserToken();
                mallUserToken.setUserId(Long.valueOf(user.getId()));
                mallUserToken.setToken(token);
                mallUserToken.setUpdateTime(now);
                mallUserToken.setExpireTime(expireTime);
                //新增一条token数据
                if (userTokenDao.insert(mallUserToken) > 0) {
                    //新增成功后返回
                    return token;
                }
            } else {
                mallUserToken.setToken(token);
                mallUserToken.setUpdateTime(now);
                mallUserToken.setExpireTime(expireTime);
                //更新
                if (userTokenDao.updateById(mallUserToken) > 0) {
                    //修改成功后返回
                    return token;
                }
            }
        }

        return ServiceResultEnum.LOGIN_ERROR.getResult();
    }

    private String getNewToken (String timeStr, Integer userId){
        String src = timeStr + userId + NumberUtil.genRandomNum(4);
        return SystemUtil.genToken(src);
    }

    public Boolean updateUserInfo(User mallUser, Integer userId) {
        User user = dao.selectById(userId);
        if (user == null) {
            GHException.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        user.setUsername(mallUser.getUsername());
        if(!StringUtils.isEmpty(mallUser.getPassword()))
            user.setPassword(mallUser.getPassword());
        //若密码为空字符，则表明用户不打算修改密码，使用原密码保存
//        if (!MD5Util.MD5Encode("", "UTF-8").equals(mallUser.getPasswordMd5())){
//            user.setPasswordMd5(mallUser.getPasswordMd5());
//        }
        user.setIntro(mallUser.getIntro());
        if (dao.updateById(user) > 0) {
            return true;
        }
        return false;
    }

    public Boolean logout(Long userId) {
        return userTokenDao.deleteById(userId) > 0;
    }

    public String register(User params) {
        QueryWrapper<User> query = new QueryWrapper<>();
        String loginName = params.getUsername();
        String password = params.getPassword();
        query.eq("username", loginName);
        List<User> users = dao.selectList(query);
        if (users.size()>0) {
            return ServiceResultEnum.SAME_LOGIN_NAME_EXIST.getResult();
        }
        User registerUser = new User();
        registerUser.setUsername(loginName);
        registerUser.setRealname("");
        registerUser.setIntro(SysConstant.USER_INTRO);
        if(StringUtils.isEmpty(password))
            password = "123456";
        registerUser.setPassword(password);
        registerUser.setEmail(params.getEmail());//设置邮箱
        registerUser.setPhone(params.getPhone());//设置电话
        if (dao.insert(registerUser) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }
}
