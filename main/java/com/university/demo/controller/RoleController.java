package com.university.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.entity.Role;
import com.university.demo.entity.response.RoleVo;
import com.university.demo.entity.response.RouteVo;
import com.university.demo.service.RoleRouteService;
import com.university.demo.service.RoleService;
import com.university.demo.service.RouteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Role控制器
 * @author redcomet
 * @since 2021-04-17
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private RoleRouteService roleRouteService;

    @PostMapping("/update")
    public ServerResponse update(@RequestBody RoleVo role) {
        if(!"sadmin".equals(role.getKey())){
            if(role!=null && role.getRoutes()!=null){
                List<RouteVo> routes = role.getRoutes();
                roleRouteService.updateRouteTable(routes,role.getKey());
            }
        }

        return roleService.updateById(role) ? ServerResponse.ofSuccess("更新成功！") : ServerResponse.ofError("更新失败！");
    }
//
//    @GetMapping("/delete/{id}")
//    public ServerResponse delete(@PathVariable("id") Integer id) {
//        return msgService.removeById(id) ? ServerResponse.ofSuccess("删除成功！") : ServerResponse.ofError("删除失败！");
//    }

//    @GetMapping("/{id}")
//    public ServerResponse query(@PathVariable("id") Integer id) {
//        return ServerResponse.ofSuccess(msgService.getById(id));
//    }

    @GetMapping("/roles/{page}")
    public ServerResponse querys(@PathVariable("page") Integer page,
                                       @RequestParam(defaultValue = "20") Integer limit) {
        List<RoleVo> roleVos = new ArrayList<>();
        Page<Role> pages = new Page<>(page, limit);
        QueryWrapper<Role> wrapper = new QueryWrapper<Role>();
        IPage<Role> iPage = roleService.page(pages, wrapper);
        List<Role> roles = iPage.getRecords();
        roles.forEach(r->{
            RoleVo rv = new RoleVo();
            BeanUtils.copyProperties(r,rv);
            List<RouteVo> routes = routeService.getRoutes(r.getKey());
            rv.setRoutes(routes);
            roleVos.add(rv);
        });
        return ServerResponse.ofSuccess(roleVos);
    }
}

