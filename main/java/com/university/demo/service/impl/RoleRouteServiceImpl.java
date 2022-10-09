package com.university.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.RoleDao;
import com.university.demo.dao.RoleRouteDao;
import com.university.demo.dao.RouteDao;
import com.university.demo.entity.Role;
import com.university.demo.entity.RoleRoute;
import com.university.demo.entity.Route;
import com.university.demo.entity.response.Meta;
import com.university.demo.entity.response.RouteVo;
import com.university.demo.service.RoleRouteService;
import com.university.demo.service.RouteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleRouteServiceImpl extends ServiceImpl<RoleRouteDao, RoleRoute> implements RoleRouteService {

    @Autowired
    private RouteDao routeDao;

    @Override
    public boolean updateRouteTable(List<RouteVo> routeVos, String key) {
        QueryWrapper<RoleRoute> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name", key);
        baseMapper.delete(wrapper);
        routeVos.forEach(routeVo -> {
            RoleRoute rr = new RoleRoute();
            rr.setRoleName(key);
            rr.setRouteId(routeVo.getId());
            baseMapper.insert(rr);
            //这个仅针对一级菜单的情况使用
            QueryWrapper<Route> childQuery = new QueryWrapper<>();
            childQuery.eq("pid", routeVo.getId());
            List<Route> children = routeDao.selectList(childQuery);
            children.forEach(child->{
                RoleRoute rr2 = new RoleRoute();
                rr2.setRoleName(key);
                rr2.setRouteId(child.getId());
                baseMapper.insert(rr2);
            });
        });
        return true;
    }
}
