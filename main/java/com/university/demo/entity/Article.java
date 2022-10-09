package com.university.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文章
 * @author redcomet
 * @since 2021-10-25
 */

@TableName("tb_article")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Article extends Model<Article> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String author;  /*发送username*/

    private String title;  /*接受username*/

    private String content;

    private String contentShort;

    private String status;

    private Integer importance;

    private String type;

    private String img;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
