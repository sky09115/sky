package com.university.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.university.demo.entity.MapData;
import org.apache.ibatis.annotations.Delete;

public interface MapDataDao extends BaseMapper<MapData> {

    @Delete("delete from  tb_map")
    int deleteAll();
}
