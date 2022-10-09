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
 * @since 2021-04-03
 */

@TableName("tb_map")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MapData extends Model<MapData> {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 关键词
     */
    private String keyword;

    /**
     * 城市
     */
    private String city;

    /**
     * 数量
     */
    private Double amount;

    @TableLogic
    private Integer deleted;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
