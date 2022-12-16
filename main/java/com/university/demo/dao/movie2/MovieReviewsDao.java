package com.university.demo.dao.movie2;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.entity.movie2.MovieReviews;
import com.university.demo.entity.movie2.MovieReviewsVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author max
 * @since 2022-12-08
 */
public interface MovieReviewsDao extends BaseMapper<MovieReviews> {
    @Select("select  a.*,b.title from movie_reviews a,movie_detail b " +
            "where a.douban_id = b.douban_id and ( b.title like CONCAT('%',#{title},'%') " +
            " or a.douban_id like   CONCAT('%',#{title},'%')  ) ")
    List<MovieReviewsVo> select(Page page, String title);

    @Select(" select avg(rating_score) from movie_detail " +
            " where production_country_area like  CONCAT('%',#{country},'%')  ")
    Double getRate(String country);

    @Select(" select avg(rating_num) from movie_detail " +
            " where production_country_area like  CONCAT('%',#{country},'%')  ")
    Double getRateNum(String country);

    @Select(" select  avg(replace(rating_5_star_weight,'%',''))  from movie_detail " +
            " where production_country_area like  CONCAT('%',#{country},'%')  ")
    Double getRate5Star(String country);
}
