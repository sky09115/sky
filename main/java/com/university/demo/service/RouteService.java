package com.university.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.university.demo.entity.Route;
import com.university.demo.entity.response.RouteVo;

import java.util.List;

public interface RouteService extends IService<Route> {
    List<RouteVo> getRoutes();

    List<RouteVo> getRoutes(String roleName);
}
