package com.university.demo.entity.traffic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("tb_road")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Road extends Model<Road> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    private Double golen; //通过样本总行驶长度(m)

    private Double speed; // 平均行程车速（km/h）

    private String time1;

    private Integer period; //时间片（一个时间片为5分钟）

    private Integer blockid;

    private Double gotime;  //通过样本总行程时间(s)

    private Double exponent; //交通指数0-2为畅通，2-4为基本畅通，4-6为缓行，6-8为较拥堵，8-10为拥堵

    private Integer flow;
}
