package com.university.demo.dao.game;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.entity.game.GameGood;
import com.university.demo.entity.game.GameGoodVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author chenqi
 * @since 2022-12-21
 */
public interface GameGoodDao extends BaseMapper<GameGood> {
    @Select("select  a.*,b.name as gamename from tb_game_good a,tb_game b " +
            " where a.gameid = b.gameid " +
            " and ( b.name like CONCAT('%',#{keyword},'%') " +
            " or a.name like   CONCAT('%',#{keyword},'%')  ) ")
    List<GameGoodVo> select(Page page, String keyword);
}