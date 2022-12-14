package com.university.demo.entity.movie2;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("movie_user_ratings")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovieUserRatings extends Model<MovieUserRatings> {

    private static final long serialVersionUID=1L;

    @TableId()
    private String review_id;

    private Integer douban_id;

    private Integer user_id;

    private Double user_movie_rating;
}
