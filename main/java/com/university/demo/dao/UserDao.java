package com.university.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.university.demo.entity.Admin;
import com.university.demo.entity.User;
import com.university.demo.entity.response.ChartData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserDao extends BaseMapper<User> {
    @Select("SELECT address as name,count(*) as value FROM `tb_user` " +
            "group by address  order by  value desc")
    List<ChartData> getProvinceUsers();

    @Select("select count(1)  from  tb_user")
    Integer getUsersCount();


}
