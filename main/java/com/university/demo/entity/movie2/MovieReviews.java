package com.university.demo.entity.movie2;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("movie_reviews")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovieReviews extends Model<MovieReviews> {

    private static final long serialVersionUID=1L;

    @TableId()
    private String review_id;

    private Integer douban_id;

    private String user_unique_name;

    private String user_head_portrait_url;

    private String user_url;

    private String user_name;

    private Integer user_movie_rating;

    private String user_movie_rating_time;

    private String user_movie_rating_content;

    private Integer user_movie_rating_agree;

    private String movie_positive_rate;

    private String movie_general_rate;

    private String movie_negative_rate;

}
