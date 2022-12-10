package com.university.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.university.demo.entity.Group;

import java.util.List;

public interface GroupService extends IService<Group> {
    int updateAmount(Integer groupId, Double amount);
}
