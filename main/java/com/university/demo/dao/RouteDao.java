package com.university.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.university.demo.entity.Route;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RouteDao extends BaseMapper<Route> {

    @Select("select a.* from tb_route a, tb_role_route b where b.role_name = #{roleName} and a.id = b.route_id and a.hidden = false and a.pid = -1")
    List<Route> getRoleRouteByRoleName(String roleName);

    @Select("select a.* from tb_route a, tb_role_route b where b.role_name = #{roleName} and a.id = b.route_id and a.hidden = false" +
            " and a.pid = #{pid}")
    List<Route> getChildRoleRouteByRoleName(String roleName, Integer pid);
}
