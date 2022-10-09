package com.university.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.university.demo.entity.Admin;
import com.university.demo.dao.AdminDao;
import com.university.demo.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author redcomet
 * @since 2021-03-30
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminDao, Admin> implements AdminService {

//    @Autowired
//    private AdminDao adminDao;

    @Override
    public Admin adminLogin(String username, String password) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("password", password);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public Integer adminLogout(String token) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("token", token);
        Admin admin = baseMapper.selectOne(wrapper);
        int ret = -1;
        if(admin!=null) {
            admin.setToken("");
            ret = baseMapper.updateById(admin);
        }
        return ret;
    }

    @Override
    public Admin info(String token) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("token", token);
        return baseMapper.selectOne(wrapper);
    }
}
