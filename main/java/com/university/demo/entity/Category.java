package com.university.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 商品类别
 * @author redcomet
 * @since
 */

@TableName("gcat_list")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category extends Model<Category> {

    private static final long serialVersionUID=1L;

    @TableId(value = "gcat_id", type = IdType.AUTO)
    private Integer categoryId;

    @TableField(value = "gcat_name")
    private String categoryName;

    @TableField(value = "gcat_enname")
    private String gcatEnname;

    @Override
    protected Serializable pkVal() {
        return this.categoryId;
    }
}
