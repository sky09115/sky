package com.university.demo.entity.order;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单
 * 会费
 * @author redcomet
 * @since 2021-10-22
 */

@TableName("tb_order")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order extends Model<Order> {

    private static final long serialVersionUID=1L;

    public static final Integer STAT_NOT_PAY = 0;
    public static final Integer STAT_PAYED = 1;
    public static final Integer STAT_CANCELED = 2;
    public static final Integer STAT_SEND = 3;
    public static final Integer STAT_FINISH = 4;
    public static final Integer STAT_RATED = 5;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    private Integer uid;  //用户ID
    private Integer iid;  //关联物品的ID

    private String phone;
    private String address;

    private String subject; //购买项目
    private String type; //类型

    private String goods_name; //商品名称

    private Double price;  //单价
    private Double amount;  //数量
    private Double discount;  //折扣
    private Double fprice;  //最终价格

    private Integer status; //0-未付 1-已付 2-取消 3-其他

    private String remark;

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
