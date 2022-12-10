package com.university.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 日志
 * @author redcomet
 * @since  04-17
 */

@TableName("tb_log")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Log extends Model<Log> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String opt;

    private String url;

    @TableField(value="http_method")
    private String httpMethod;

    private String ip;

    @TableField(value="class_method")
    private String classMethod;

    private String args;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT, value="create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE, value="update_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
