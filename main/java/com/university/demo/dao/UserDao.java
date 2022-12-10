package com.university.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.university.demo.entity.User;
import com.university.demo.entity.response.ChartData;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author max
 * @since 2022-12-08
 */
public interface UserDao extends BaseMapper<User> {
    @Select("select job as name, count(*) as value from  tb_user group by job")
    List<ChartData> getUserJob();

    @Select("select  count(*)  from  tb_user where gender =#{gender} and job = #{job}")
    Integer getUserJobSex(String gender, String job);
}
