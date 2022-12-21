package com.university.demo.entity.game;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("tb_game_good")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GameGood extends Model<GameGood> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    // good_id
    private String gid;

    private String gameid;

    private String name;

    private Integer isall;

    private String customlink;

    private Integer isenabled;

    private Integer recommendways;

    private String supportransactionmode;

    private String update_time;

}
