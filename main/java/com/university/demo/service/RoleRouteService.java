package com.university.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.university.demo.entity.RoleRoute;
import com.university.demo.entity.Route;
import com.university.demo.entity.response.RouteVo;

import java.util.List;

public interface RoleRouteService extends IService<RoleRoute> {

    boolean updateRouteTable(List<RouteVo> routeVos, String key);
}
