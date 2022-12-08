package com.university.demo.controller;


import com.university.demo.controller.base.BaseController;
import com.university.demo.entity.User;
import org.springframework.web.bind.annotation.*;
/**
 * @author 麦克斯韦
 * @since  2022-12-08
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User> {

}

