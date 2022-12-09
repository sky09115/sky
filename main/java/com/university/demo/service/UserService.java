package com.university.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.university.demo.entity.User;

public interface UserService extends IService<User> {
    User login(String username, String password);

    User info(Integer id);
}
