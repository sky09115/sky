package com.university.demo.entity.movie2;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("movie_tag")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovieTag extends Model<MovieTag> {

    private static final long serialVersionUID=1L;

    private String tag_name;

    private Integer tag_id;
}
