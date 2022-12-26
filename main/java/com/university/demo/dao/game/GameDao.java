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

    @Select("select  type as name, avg(downloads) as value from  tb_game " +
            "group by type order by avg(downloads) desc limit 10")
    List<ChartData> getTypeDownloadRank2();

    @Select("select  type as name, count(1) as value from  tb_game " +
            "group by type order by count(1) desc limit 10")
    List<ChartData> getTypeCount();

    @Select(" select  gametypename as name,count(1)  as value from  tb_game where gametypename<>'' " +
            "    group by gametypename order by count(1) desc limit 7")
    List<ChartData> getGameTypeCount();

    @Select(" select  gametypename as name,sum(downloads)  as value from  tb_game where gametypename<>'' " +
            "    group by gametypename order by sum(downloads) desc limit 7")
    List<ChartData> getGameTypeDownload();
}
