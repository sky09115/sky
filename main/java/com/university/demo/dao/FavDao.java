package com.university.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.university.demo.entity.Concern;
import com.university.demo.entity.Fav;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FavDao extends BaseMapper<Fav> {

    @Select("select distinct sid,rid,content from tb_fav where sid = #{username} limit 100")
    List<Fav> getFavs(String username);
}
