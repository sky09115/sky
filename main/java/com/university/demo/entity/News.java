package com.university.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author redcomet
 * @since 2022-06-24
 */

@TableName("new")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class News extends Model<News> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String url;

    private String maintxt;

    private String ab;

    private String time;

    private String keyword;

    private String city;

    private String web;

    private Integer star;

    private Integer thumb;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
