package com.university.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.GroupDao;
import com.university.demo.dao.LogDao;
import com.university.demo.entity.Group;
import com.university.demo.entity.Log;
import com.university.demo.entity.response.PieData;
import com.university.demo.service.GroupService;
import com.university.demo.service.LogService;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceImpl extends ServiceImpl<GroupDao, Group> implements GroupService {

    @Override
    public int updateAmount(Integer groupId, Double amount) {
        Group group = baseMapper.selectById(groupId);
        Double originalAmount = group.getAmount();
        group.setAmount(originalAmount + amount);
        return baseMapper.updateById(group);
    }

    @Override
    public List<PieData> groupUsers() {
        return baseMapper.groupUsers();
    }
}
