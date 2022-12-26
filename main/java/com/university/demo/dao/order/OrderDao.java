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
}
