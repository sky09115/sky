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
 * 品牌
 * @author redcomet
 * @since
 */

@TableName("bcat_list")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Brand extends Model<Brand> {

    private static final long serialVersionUID=1L;

    @TableId(value = "bcat_id", type = IdType.AUTO)
    private Integer id;

    private String bcatName;

    private String bcatEnname;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
