package com.university.demo.dao.movie2;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.entity.movie2.MovieUserRatings;
import com.university.demo.entity.movie2.MovieUserRatingsVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author max
 * @since 2022-12-08
 */
public interface MovieUserRatingsDao extends BaseMapper<MovieUserRatings> {
    @Select("select  a.*,b.title from movie_user_ratings a,movie_detail b," +
            "movie_user c " +
            "where a.douban_id = b.douban_id and b.title like CONCAT('%',#{title},'%') " +
            " and a.user_id = c.user_id and c.user_name  like CONCAT('%',#{username},'%') ")
    List<MovieUserRatingsVo> select(Page page, String title, String username);
}
