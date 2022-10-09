package com.university.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.MapDataDao;
import com.university.demo.entity.MapData;
import com.university.demo.service.MapDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapDataServiceImpl extends ServiceImpl<MapDataDao, MapData> implements MapDataService {

    @Autowired
    private MapDataDao mapDataDao;

    @Override
    public int deleteAll() {
        return mapDataDao.deleteAll();
    }
}
