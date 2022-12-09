package com.university.demo.entity.response;

import com.university.demo.entity.Role;
import lombok.Data;

import java.util.List;

@Data
public class RoleVo extends Role {
    private List<RouteVo> routes;
}
