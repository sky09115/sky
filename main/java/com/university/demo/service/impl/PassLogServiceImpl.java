package com.university.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.LogDao;
import com.university.demo.dao.PassLogDao;
import com.university.demo.entity.Log;
import com.university.demo.entity.PassLog;
import com.university.demo.service.LogService;
import com.university.demo.service.PassLogService;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PassLogServiceImpl extends ServiceImpl<PassLogDao, PassLog> implements PassLogService {

    @Override
    public Integer countPass(Integer id){
        return baseMapper.countPass(id);
    }
}
