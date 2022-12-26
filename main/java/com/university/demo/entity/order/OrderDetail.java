package com.university.demo.entity.order;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("tb_order_detail")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetail extends Model<OrderDetail> {

    private static final long serialVersionUID=1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String oid;  //关联 order id

    private Integer iid;

    private Integer uid;

    private String goods_name;

    private String goods_logo;

    private Integer amount;

    private Double price;

    private Double discount;

    private Double fprice;

    private Integer status;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime create_time;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime update_time;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
