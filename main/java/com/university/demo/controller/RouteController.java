package com.university.demo.controller;

import com.university.demo.entity.system.ServerResponse;
import com.university.demo.entity.response.RouteVo;
import com.university.demo.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Route控制器
 * @author redcomet
 * @since 2021-04-25
 */
@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping("/getRoutes/{role}")
    public ServerResponse getRoutesByRole(@PathVariable("role") String roleName) {
        List<RouteVo> routes = routeService.getRoutes(roleName);
        return ServerResponse.ofSuccess(routes);
    }

    @GetMapping("/getRoutes")
    public ServerResponse getRoutes() {
        List<RouteVo> routes = routeService.getRoutes();
        return ServerResponse.ofSuccess(routes);
    }
}

