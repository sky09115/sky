package com.university.demo.dao.movie2;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.entity.movie2.ImdbRatings;
import com.university.demo.entity.movie2.ImdbRatingsVo;
import com.university.demo.entity.movie2.MovieUserRatings;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author max
 * @since 2022-12-08
 */
public interface ImdbRatingsDao extends BaseMapper<ImdbRatings> {
    @Select("select  a.*,b.title from imdb_ratings a,movie_detail b " +
            "where a.douban_id = b.douban_id and b.title like CONCAT('%',#{title},'%') " +
            "  ")
    List<ImdbRatingsVo> select(Page page, String title);
}
