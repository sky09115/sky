package com.university.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author
 * @since 2022年11月20日
 */

@TableName("tb_province")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Province extends Model<Province> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String dm;
    private String mc;
    private String ls;
    private String cc;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
