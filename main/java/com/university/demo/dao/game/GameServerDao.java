package com.university.demo.dao.game;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.entity.game.Game;
import com.university.demo.entity.game.GameServer;
import com.university.demo.entity.game.GameServerVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author chenqi
 * @since 2022-12-21
 */
public interface GameServerDao extends BaseMapper<GameServer> {
    @Select("select  a.*,b.name as gamename, b.id as gamekey from tb_server a,tb_game b " +
            " where a.gameid = b.gameid " +
            " and ( b.name like CONCAT('%',#{keyword},'%') " +
            " or a.name like   CONCAT('%',#{keyword},'%')  ) ")
    List<GameServerVo> select(Page page, String keyword);
}
