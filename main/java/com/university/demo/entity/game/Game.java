package com.university.demo.entity.game;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("tb_game")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Game extends Model<Game> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    private String gameid;

    private String name;

    private String identify;

    private String type;

    private Integer ishot;

    private String icon;

    private Integer starlevel;

    private Integer downloads;

    private String gametypename;

    private String shopimageurl;

    private String update_time;
}
