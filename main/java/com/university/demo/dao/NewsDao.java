package com.university.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.university.demo.entity.Admin;
import com.university.demo.entity.News;
import com.university.demo.entity.response.ChartData;
import com.university.demo.entity.response.ThreeData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NewsDao extends BaseMapper<News> {

    @Select("select *  from tb_news  " +
            "where status = true " +
            "and deleted = false " +
            "and author in ( " +
            "select  rid from tb_concern " +
            "where sid = #{username} and deleted = false " +
            ") " +
            "order by star desc ")
    List<News> getBookMarks(String username);

    @Select("select  goods_name as name1, price as name3  from  goods_list order by price desc  limit 10 ")
    List<ThreeData> getRank();

    @Select("select  iid as name1 , sum(rate) as name2   from  tb_rate group by iid order by sum(rate) desc  limit 10 ")
    List<ThreeData> getTopRate();
}
