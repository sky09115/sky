package com.university.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.university.demo.entity.Product;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductDao extends BaseMapper<Product> {

    @Select(" select price_down from price_list where goods_id = #{pid} order by upd_time desc limit 1")
    Double getPrice(Integer pid);

    @Select(" select  a.goods_id as id, a.*  from goods_list a " +
            "where goods_id  in (select product_id from  tb_collect where user_id = #{userId} " +
            " and deleted = false )" )
    List<Product> getColloectProducts(Integer userId);

    @Select(" select  a.goods_id as id, a.*  from goods_list a " +
            "where goods_id  in (select product_id from  tb_cart where user_id = #{userId} " +
            " and deleted = false )" )
    List<Product> getShoppingCart(Integer userId);

    @Select("select sum(amount) from  tb_order")
    Double getOrderSum();

    @Select("select count(distinct bcat_id) from  goods_list")
    Integer getAuthorCount();
}
