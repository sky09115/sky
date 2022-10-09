package com.university.demo.service;

import com.university.demo.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author redcomet
 * @since 2021-03-29
 */
public interface AdminService extends IService<Admin> {

    Admin adminLogin(String username, String password);

    Integer adminLogout(String token);

    /* 根据token 获取Admin */
    Admin info(String token);
}
