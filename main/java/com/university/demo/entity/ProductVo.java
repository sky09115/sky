package com.university.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Product
 * @author
 * @since 2022年7月12日15:59:10
 */

@Data
public class ProductVo extends Product {
    private String platform;
    private String brand;
    private String group;
    private Integer num;
}
