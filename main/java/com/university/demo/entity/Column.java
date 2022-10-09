package com.university.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * 栏目
 * @author redcomet
 * @since 2021-10-25
 */
@TableName("tb_column")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Column extends Model<Column> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;  /*名称*/

    private String remark;  /*备注*/

    private String engTitle;

    private String type;

    private String img; /*图片*/

    private Integer status; /*状态*/

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
