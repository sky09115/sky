package com.university.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.entity.Order;
import com.university.demo.entity.response.ChartData;
import com.university.demo.entity.response.OrderVo;
import com.university.demo.entity.response.ThreeData;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author redcomet
 * @since
 */
public interface OrderDao extends BaseMapper<Order> {

    @Select("select  a.*,b.realname from tb_order a,tb_user b where a.user_id = b.id and b.realname " +
            " like CONCAT('%',#{userName},'%') and a.group_id = #{groupId} " +
            "  and  a.deleted = false  and a.type like  CONCAT('%',#{type},'%')")
    List<OrderVo> select(Page page, String userName, Integer groupId, String type);

    @Select("select  a.*,b.realname from tb_order a,tb_user b " +
            "where a.user_id = b.id and b.realname like CONCAT('%',#{userName},'%') " +
            "  and  a.deleted = false and a.type like  CONCAT('%',#{type},'%')")
    List<OrderVo> select2(Page page, String userName, String type);

    @Select("select  a.*,b.realname,c.remark as deptname from tb_order a,tb_user b,tb_group c  " +
            "where a.user_id = b.id and b.id = #{userId} " +
            "and c.id = a.group_id  and  a.deleted = false and a.type like  CONCAT('%',#{type},'%')")
    IPage<OrderVo> select3(Page page, Integer userId, String type);

    @Select("select  realname as name,sum(amount) as value from (" +
            "select  u.realname,o.*  from  tb_order o, tb_user u  where o.user_id = u.id and o.type ='购买') x " +
            "group by realname order by sum(amount) desc")
    List<ChartData> getDepositRank();

    @Select("select  realname as name,count(*) as value from (" +
            "select  u.realname,o.*  from  tb_order o, tb_user u  where o.user_id = u.id and o.type ='购买') x " +
            "group by realname order by count(*) desc")
    List<ChartData> getUserOrderRank();

    @Select("select count(id) from tb_order where status = #{status}")
    Double getCount(Integer status);

    @Select("select sum(amount) from tb_order where status = #{status}")
    Double getAmountSum(Integer status);

    @Select("select  sum(amount)  from  tb_order where year(create_time) = #{year} and month(create_time) = #{month} ")
    Double getAmountByMonth(String year, String month);

    @Select("select  count(1)  from  tb_order where year(create_time) = #{year} and month(create_time) = #{month} ")
    Double getCountByMonth(String year, String month);

    @Select("select sum(amount) from tb_order where status < #{status}")
    Double getNotAmountSum(Integer status);

    @Select("select  bcat_name as name,count(1)  as value " +
            "from goods_list g, bcat_list b " +
            "where g.bcat_id = b.bcat_id  group by bcat_name ")
    List<ChartData> getBrand();

    @Select(" select  gcat_name as name,count(1)  as value " +
            "    from goods_list g, gcat_list b " +
            "    where g.gcat_id = b.gcat_id  group by gcat_name ")
    List<ChartData> getCategory();

    @Select("select  goods_name as name1,sum(num) as value1 " +
            ",sum(price) as value2  from  tb_order_detail d " +
            "group by goods_name order by sum(price) desc")
    List<ThreeData> getSale();

    @Select("select  sum(num)  from  tb_order_detail  " +
            "where substr(create_time,6,2) = #{month} ")
    Integer getSaleByMonth(String month);

    @Select("select  sum(price)  from  tb_order_detail  " +
            "where substr(create_time,6,2) = #{month} ")
    Integer getPriceByMonth(String month);

    @Select("select d from (select  distinct DATE_FORMAT(create_time,'%Y-%m-%d') as d   from  tb_order  " +
            " order by create_time desc limit 15) as tt order by d")
    List<String> orderDay();

    @Select("select  a.bcat_name as name,count(1) as value from  (" +
            "select  g.*, b.bcat_name  from  goods_list g, bcat_list b where g.bcat_id = b.bcat_id " +
            "and bcat_name <> '未知'  ) a " +
            "group by a.bcat_name order by count(1) desc limit 15 ")
    List<ChartData> getBook();


    @Select(" select  bcat_name as name, avg(price) as value  " +
            "from  goods_list g, bcat_list b " +
            "where g.bcat_id = b.bcat_id  group by bcat_name ")
    List<ChartData> getSellers();

    @Select("   select  gcat_name as name, avg(price) as value  from  goods_list g, gcat_list b " +
            "where g.gcat_id = b.gcat_id  group by gcat_name  limit 15")
    List<ChartData> getHotRank();

    @Select("   select goods_name as name, price as value from goods_list limit 5  ")
    List<ChartData> getIndustryFields();


    @Select(" select  goods_name as name ,price as value from  goods_list  where gcat_id = 2 limit 15 ")
    List<ChartData> getProvinceRank();

    @Select(" select  goods_name as name ,price as value from  goods_list  where gcat_id = 1 limit 15 ")
    List<ChartData> getCityRank();

    @Select(" select  goods_name as name ,price as value from  goods_list  where gcat_id = 3 limit 15 ")
    List<ChartData> getDistrictRank();



    @Select("select  sum(o.price)  from  tb_order_detail o, goods_list g" +
            " where o.goods_id = g.goods_id and g.gcat_id = 2" +
            " and year(create_time) = #{year} and month(create_time) = #{month}")
    Double getCarsByMonth(String year, String month);

    @Select("select  sum(o.price)  from  tb_order_detail o, goods_list g" +
            " where o.goods_id = g.goods_id and g.gcat_id = 1" +
            " and year(create_time) = #{year} and month(create_time) = #{month}")
    Double getTicketsByMonth(String year, String month);

    @Select("select  sum(o.price)  from  tb_order_detail o, goods_list g" +
            " where o.goods_id = g.goods_id and g.gcat_id = 3" +
            " and year(create_time) = #{year} and month(create_time) = #{month}")
    Double getInsuresByMonth(String year, String month);

    @Select("select sum(amount)  from  tb_order")
    Double getOrderSum();
}
