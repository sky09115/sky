package com.university.demo.controller;


import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.anotation.SysLog;
import com.university.demo.controller.base.BaseController;
import com.university.demo.controller.base.MyWrapper;
import com.university.demo.entity.Route;
import com.university.demo.entity.User;
import com.university.demo.entity.response.RouteVo;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.entity.system.SysConstant;
import com.university.demo.service.RouteService;
import com.university.demo.service.UserService;
import com.university.demo.service.impl.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 麦克斯韦
 * @since  2022-12-08
 * @说明： 1. list 支持一个搜索参数search，search_fields 支持多字段模糊查询
 *        2. list search_filter为拓展的get请求参数,配置了前台可以不传,不影响查询结果
 */
@RestController
@RequestMapping("/route")
public class RouteController extends BaseController<Route> {

    @Autowired
    private RouteService routeService;
    protected String[] search_fields = new String[]{ };
    protected String[] search_filter = new String[]{ };

    @Override
    @GetMapping("/")
    public ServerResponse list(HttpServletRequest request,
                               @RequestParam(defaultValue = "") String search,
                               @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "15") Integer limit
                               ) {
        Page<Route> pages = new Page<>(page, limit);
        MyWrapper<Route> wrapperFactory = new MyWrapper<>();
        QueryWrapper<Route> wrapper = wrapperFactory.init(request, search, search_fields, search_filter);
        IPage<Route> iPage = baseSerivce.page(pages, wrapper);
        return ServerResponse.ofSuccess(iPage);
    }

    // 根据角色的名字来获取角色相应可以访问到的菜单
    @GetMapping("/getRoutes/{role}")
    public ServerResponse getRoutesByRole(@PathVariable("role") String roleName) {
        List<RouteVo> routes = routeService.getRoutes(roleName);
        return ServerResponse.ofSuccess(routes);
    }
}

