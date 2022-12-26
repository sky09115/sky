package com.university.demo.dao.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.university.demo.entity.order.Order;
import com.university.demo.entity.response.ChartData;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author max
 * @since 2022-12-22
 */
public interface OrderDao extends BaseMapper<Order> {
    @Select("select realname as name, sum(fprice) as value from  tb_user u, tb_order o " +
            " where u.id = o.uid group by realname")
    List<ChartData> getPay();


    @Select("select  sum(fprice)  from  tb_order where year(create_time) = #{year} and month(create_time) = #{month} ")
    Double getAmountByMonth(String year, String month);

    @Select("select  count(1)  from  tb_order where year(create_time) = #{year} and month(create_time) = #{month} ")
    Double getCountByMonth(String year, String month);

    @Select("select sum(fprice) from  tb_order")
    Double getSum();
}
