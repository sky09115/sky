package com.university.demo.entity;

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

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    private Integer userId;

    private Integer groupId;

    private String reason;

    private Double amount;

    private Integer status;

    private String type;

    private String remark;

//    private Integer oid;  // 关联物的id

    private String phone;

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
