package com.university.demo.entity.movie2;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("imdb_ratings")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImdbRatings extends Model<ImdbRatings> {

    private static final long serialVersionUID=1L;

    @TableId()
    private String imdb_id;

    private Integer douban_id;

    private Double imdb_rating;

    private String rating_scores;

    private String rating_scores_weights;

    private String rating_scores_votes;

    private String age_all;

    private String age_less_than_18;

    private String age_18_29;

    private String age_30_44;

    private String age_more_than_45;

    private String male_ratings;

    private String female_ratings;
}
