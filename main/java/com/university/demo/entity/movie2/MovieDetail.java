package com.university.demo.entity.movie2;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("movie_detail")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovieDetail extends Model<MovieDetail> {

    private static final long serialVersionUID=1L;

    private Integer douban_id;

    private String title;

    private String brief_instruction;

    private String directors;

    private String screenwriters;

    private String casts;

    private String types;

    private String production_country_area;

    private String language;

    private String publish_date;

    private String runtime;

    private Double rating_score;

    private Integer rating_star;

    private Integer rating_num;

    private String user_movie_rating_time;

    private String user_movie_rating_content;

    private Integer user_movie_rating_agree;

    private String rating_5_star_weight;

    private String rating_4_star_weight;

    private String rating_3_star_weight;

    private String rating_2_star_weight;

    private String rating_1_star_weight;

    private String better_than;

    private String douban_url;

    private String cover_url;

    private String imdb_url;

    private String img_list;
}
