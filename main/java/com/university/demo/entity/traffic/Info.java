package com.university.demo.entity.traffic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("tb_info")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Info extends Model<Info> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    private String lm;

    private String qs;

    private String lx;

    private String dj;

    private Integer cd;

    private String rdmj;  //路段面积

    private Integer rxdmj;
}
