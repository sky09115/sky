package com.university.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("price_list")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Price extends Model<Price> {

    private static final long serialVersionUID=1L;

    @TableId(value = "price_id", type = IdType.AUTO)
    private Integer priceId;

    private Integer goodsId;
    private String priceNow;
    private String priceDown;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updTime;

    @Override
    protected Serializable pkVal() {
        return this.priceId;
    }
}
