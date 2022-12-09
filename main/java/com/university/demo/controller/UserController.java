package com.university.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.controller.base.BaseController;
import com.university.demo.entity.User;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.util.MyWrapper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Consumer;

/**
 * @author 麦克斯韦
 * @since  2022-12-08
 * @说明： 1. list 支持一个搜索参数search，search_fields 支持多字段模糊查询
 *        2. list search_filter为拓展的get请求参数,配置了前台可以不传,不影响查询结果
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User> {
    protected String[] search_fields = new String[]{"username", "realname"};
    protected String[] search_filter = new String[]{"roles", "major"};

    @Override
    @GetMapping("/")
    public ServerResponse list(HttpServletRequest request,
                               @RequestParam(defaultValue = "") String search,
                               @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "15") Integer limit
                               ) {
        Page<User> pages = new Page<>(page, limit);
        MyWrapper<User> wrapperFactory = new MyWrapper<>();
        QueryWrapper<User> wrapper = wrapperFactory.init(request, search, search_fields, search_filter);
        IPage<User> iPage = baseSerivce.page(pages, wrapper);
        return ServerResponse.ofSuccess(iPage);
    }
}

