package com.university.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.university.demo.entity.Admin;
import com.university.demo.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author redcomet
 * @since 2021-03-29
 */
public interface UserService extends IService<User> {

    User login(String username, String password);

    User info(Integer id);
}
