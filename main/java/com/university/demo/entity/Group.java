package com.university.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户组
 * @author redcomet
 * @since  12-10
 */

@TableName("tb_group")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Group extends Model<Group> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value="user_id")
    private Integer userId;
    @TableField( value="group_id")
    private Integer groupId;

    private String remark;

    private Double amount;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT, value="create_time")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE, value="update_time")
    private LocalDateTime updateTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
