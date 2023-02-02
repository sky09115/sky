package com.university.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.university.demo.entity.User;
import com.university.demo.entity.response.ChartData;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author max
 * @since 2022-10-08
 */
public interface VisDao extends BaseMapper<User> {

    @Select("  select count(1) from tb_metro ")
    Integer dash00();

    @Select("  select sum(flow) from tb_flow ")
    Integer dash01();
    @Select(" select  concat (first_time, '-',last_time) as name , flow as value  from  tb_flow2 order by flow desc limit 10  ")
    List<ChartData> chart1();

    @Select(" select  concat (first_time, '-',last_time) as name , flow as value  from  tb_flow2 " +
            " where xl ='是' order by flow desc limit 10  ")
    List<ChartData> chart2();

    @Select(" select m.name, f.flow as value from  tb_flow f, tb_metro m where m.code = f.site_code and xl ='是' order by flow desc limit 10;  ")
    List<ChartData> chart3();

    @Select("   select lm as name, avg(exponent) as value from " +
            "  (select b.lm, a.*  from  tb_road a, tb_info b " +
            " where a.blockid = b.id) x group by lm order by value desc ")
    List<ChartData> dash1();

    @Select("   select  period as name ,avg(exponent) as value from tb_road " +
            "where period>=72 and period <=96 " +
            "group by period " +
            "order by period asc ")
    List<ChartData> dash2();

    @Select("   select lm as name, sum(exponent) as value from " +
            "  (select b.lm, a.*  from  tb_road a, tb_info b " +
            " where a.blockid = b.id) x group by lm order by value desc ")
    List<ChartData> dash3();

    @Select("  select lm as name, avg(speed) as value from  " +
            "  (select b.lm, a.*  from  tb_road a, tb_info b " +
            " where a.blockid = b.id) x group by lm order by value desc ")
    List<ChartData> dash4();

    @Select("  select lm as name, avg(gotime) as value from  " +
            "  (select b.lm, a.*  from  tb_road a, tb_info b " +
            " where a.blockid = b.id) x group by lm order by value desc " +
            "  ")
    List<ChartData> dash5();

    @Select("  select  qs as name, count(1) as value  from  tb_info group by qs ")
    List<ChartData> dash6();

    @Select(" select title as name, rating_score as value " +
            " from  movie_detail order by rating_score desc limit 10 ")
    List<ChartData> get32();

    @Select(" select title as name, rating_score as value " +
            " from  movie_detail order by rating_score desc limit 10 ")
    List<ChartData> get33();

    @Select("select distinct d from (select  distinct DATE_FORMAT(user_movie_rating_time,'%Y-%m-%d') as d  " +
            "            from  movie_reviews " +
            " order by DATE_FORMAT(user_movie_rating_time,'%Y-%m-%d') desc limit 15) as tt order by d")
    List<String> review_day();





}
