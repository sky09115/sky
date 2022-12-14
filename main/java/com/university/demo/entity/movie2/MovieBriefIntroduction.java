package com.university.demo.entity.movie2;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("movie_brief_introduction")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovieBriefIntroduction extends Model<MovieBriefIntroduction> {

    private static final long serialVersionUID=1L;

    @TableId()
    private Integer douban_id;

    private String title;

    private Double rate;

    private Integer star;

    private String directors;

    private String casts;

    private String url;

    private String cover;

    private String cover_x;

    private String cover_y;
}
