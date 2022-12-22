package com.university.demo.entity.order;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author max
 * @since  2022-12-22
 */

@TableName("tb_cart")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cart extends Model<Cart> {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ExcelProperty(index = 0)
    private Integer id;
    /**
     * 物品ID
     */
    private Integer iid;
    /**
     * 用户名
     */
    private Integer uid;

    /**
     * 价格
     */
    private Double price;

    /**
     * 数量
     */
    private Integer amount;

    /**
     * 折扣
     */
    private Double discount;


    /**
     * 最终价格
     */
    private Double fprice;

    /**
     * 备注
     */
    private String remark;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
