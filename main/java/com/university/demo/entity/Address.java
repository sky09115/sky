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
 * @author lequal
 * @since 2022年9月14日
 */

@TableName("tb_address")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Address extends Model<Address> {

    private static final long serialVersionUID=1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer uid;
    private String name;
    private String phone;
    private String address;
    private String de;   /* 默认的地址 */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
