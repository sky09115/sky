package com.university.demo.dao.game;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.university.demo.entity.game.Game;
import com.university.demo.entity.response.ChartData;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author chenqi
 * @since 2022-12-21
 */
public interface GameDao extends BaseMapper<Game> {
    @Select("select  name, downloads as value from  tb_game order by downloads desc limit 10")
    List<ChartData> getDownloadRank();

    @Select("select  gametypename as name, avg(downloads) as value from  tb_game " +
            "group by gametypename order by avg(downloads) desc limit 10")
    List<ChartData> getTypeDownloadRank();
}
