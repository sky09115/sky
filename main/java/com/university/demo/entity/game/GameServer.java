package com.university.demo.entity.game;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("tb_server")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GameServer extends Model<GameServer> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    // good_id
    private String did;

    private String gameid;

    private String name;

    private Integer isclose;

    private String update_time;

}
