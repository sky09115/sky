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

@TableName("tb_forecast")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Forecast extends Model<Forecast> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer max_degree;
    private Integer min_degree;
    private String weather;
    private String wind_direction;
    private Integer wind_power;
    private String time1;
    private String night_weather;
    private String night_wind_direction;
    private Integer night_wind_power;
    private String update_time;
    private String province;
    private String city;
    private String county;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
