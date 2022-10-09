package com.university.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.university.demo.entity.SmsCode;
import com.university.demo.entity.User;
import com.university.demo.entity.response.ChartData;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tesla
 * @since 2022-10-08
 */
public interface VisDao extends BaseMapper<User> {

    @Select(" select '歌曲' as name , count(1)  as value from  tb_song2 ")
    ChartData getC1();

    @Select(" select '专辑' as name , count(1)  as value from  tb_album ")
    ChartData getC2();

    @Select(" select '音乐人' as name , count(1)  as value from  tb_artist2 ")
    ChartData getC3();

    @Select(" select '歌词' as name , count(1)  as value from  tb_lyric ")
    ChartData getC4();

    @Select(" select 'MV' as name , count(1)  as value from  tb_song2 where mv<>'0' ")
    ChartData getC5();

    @Select(" select '歌手' as name , count(1)  as value from  tb_artist2 where indentities like '%歌手%' ")
    ChartData getC6();

    @Select(" select '作曲' as name , count(1)  as value from  tb_artist2 where indentities like '%作曲%' ")
    ChartData getC7();

    @Select(" select '作词' as name , count(1)  as value from  tb_artist2 where indentities like '%作词%' ")
    ChartData getC8();

    @Select("select  singerName as name , count(1) as value from  tb_song2 group by singerName order by count(1) desc limit 15")
    List<ChartData> get11();

    @Select("select  singerName as name , count(1) as value, count(1) from  tb_song2 group by singerName having count(1)>200 order by count(1) desc limit 15")
    List<ChartData> get13();

    @Select("select  `name` as name , cast(commentCount as signed) as `value` from  tb_album  " +
            " order by cast(commentCount as signed) desc limit 5")
    List<ChartData> get31();

    @Select("select  avg( cast(commentCount as signed)) as value from  tb_album  ")
    Integer get32();

    @Select("select  `company` as name , count(1) as value from  tb_album group by `company` order by count(1) desc limit 50 " )
    List<ChartData> get33();
}
