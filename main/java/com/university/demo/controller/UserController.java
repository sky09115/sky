package com.university.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.controller.base.BaseController;
import com.university.demo.entity.User;
import com.university.demo.entity.system.ServerResponse;
import org.springframework.web.bind.annotation.*;
/**
 * @author 麦克斯韦
 * @since  2022-12-08
 * @说明： 1. list 支持一个搜索参数search，如果需要条件较多的搜索，则需要专门定制接口
 *        2. 特别定制查询接口也用get方法，这样可以获得更好的兼容性
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User> {
    protected String[] search_fields = new String[]{"username", "realname"};

    @Override
    @GetMapping("/")
    public ServerResponse list(@RequestParam(defaultValue = "") String search,
                               @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "15") Integer limit) {
        Page<User> pages = new Page<>(page, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        for(String field: search_fields) wrapper.or().like(field, search);
        IPage<User> iPage = baseSerivce.page(pages, wrapper);
        return ServerResponse.ofSuccess(iPage);
    }
}

