package com.university.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 评分（推荐）
 * @author redcomet
 * @since 2021-01-03
 */

@TableName("tb_rate")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Rate extends Model<Rate> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer uid;

    private Integer iid;

    private Double rate;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
