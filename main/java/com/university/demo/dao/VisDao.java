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


    @Select(" select substr(title,1,10) as name, rating_score as value " +
            " from  movie_detail order by rating_score desc limit 10 ")
    List<ChartData> get23();

    @Select(" select title as name, rating_score as value " +
            " from  movie_detail order by rating_score desc limit 10 ")
    List<ChartData> get31();

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
