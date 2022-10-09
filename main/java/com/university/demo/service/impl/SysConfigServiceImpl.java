package com.university.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.SysConfigDao;
import com.university.demo.entity.SysConfig;
import com.university.demo.service.SysConfigService;
import org.springframework.stereotype.Service;

@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfig> implements SysConfigService {

    @Override
    public SysConfig loadSysConfig(String configKey) {
        QueryWrapper<SysConfig> query = new QueryWrapper<>();
        query.eq("config_key", configKey);
        return baseMapper.selectOne(query);
    }
}
