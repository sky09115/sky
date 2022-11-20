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
 * @author
 * @since 2022年11月20日
 */

@TableName("tb_weather")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Weather extends Model<Weather> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer degree;
    private Integer humidity;
    private Double precipitation;
    private Integer pressure;
    private String weather;
    private String update_time;
    private Integer wind_power;
    private String province;
    private String city;
    private String county;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
