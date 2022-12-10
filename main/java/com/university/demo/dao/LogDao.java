package com.university.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.university.demo.entity.Log;
import com.university.demo.entity.response.ChartData;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author redcomet
 * @since  04-17
 */
public interface LogDao extends BaseMapper<Log> {

    @Select("select distinct d from (select  distinct DATE_FORMAT(create_time,'%Y-%m-%d') as d  " +
            "            from  tb_log where opt = #{opt}" +
            " order by DATE_FORMAT(create_time,'%Y-%m-%d') desc limit 15) as tt order by d")
    List<String> chart(String opt);

    @Select("select  remark as name,count(*) as value from (" +
            "select  g.remark,u.realname  from  tb_group g, tb_user u where u.group_id = g.id" +
            ") a" +
            " group by remark order by value desc")
    List<ChartData> groupManCount();

    @Select("select  name,count(*) as value from (" +
            "select  r.name,u.realname  from  tb_role r, tb_user u where u.roles = r.key" +
            ") a" +
            " group by name  order by value desc")
    List<ChartData> roleManCount();
}
