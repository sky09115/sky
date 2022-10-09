package com.university.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.university.demo.entity.SysConfig;

public interface SysConfigService extends IService<SysConfig> {

    SysConfig loadSysConfig(String configKey);

}
