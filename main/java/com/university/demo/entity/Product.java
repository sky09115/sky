package com.university.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Product
 * @author
 * @since 2022年7月12日15:54:10
 */

@TableName("goods_list")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product extends Model<Product> {

    private static final long serialVersionUID=1L;

    @TableId(value = "goods_id", type = IdType.AUTO)
    private Integer id;

    private Integer itemId;
    private Integer platId;
    private String storeName;
    private String goodsName;
    private String goodsLogo;
    private String goodsLink;
    private String price;
    private Integer goodsPid;
    private Integer gcatId;   //分类
    private Integer bcatId;   //品牌

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
