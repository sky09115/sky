package com.university.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.university.demo.entity.MapData;

public interface MapDataService extends IService<MapData> {
    int deleteAll();
}
