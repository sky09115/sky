package com.university.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.university.demo.entity.Province;
import com.university.demo.entity.Weather;
import com.university.demo.entity.response.ChartData;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WeatherDao extends BaseMapper<Weather> {

    @Select("select SUBSTR(update_time,1,8) as name, degree as value from  tb_weather" +
            " where city = #{city} and update_time in  (" +
            "select  *  from   (select  distinct update_time from tb_weather where   city = #{city} " +
            "order by update_time desc limit 7 ) as t" +
            ")")
    List<ChartData> getHistoryWeather(String city);

    @Select("select SUBSTR(update_time,1,8) as name, precipitation as value from  tb_weather" +
            " where city = #{city} and update_time in  (" +
            "select  *  from   (select  distinct update_time from tb_weather where   city = #{city} " +
            "order by update_time desc limit 7 ) as t" +
            ")")
    List<ChartData> getHistoryPrecipitation(String city);

    @Select("select SUBSTR(update_time,1,8) as name, humidity as value from  tb_weather" +
            " where city = #{city} and update_time in  (" +
            "select  *  from   (select  distinct update_time from tb_weather where   city = #{city} " +
            "order by update_time desc limit 7 ) as t" +
            ")")
    List<ChartData> getHistoryHumidity(String city);

    @Select("select SUBSTR(update_time,1,8) as name, wind_power as value from  tb_weather" +
            " where city = #{city} and update_time in  (" +
            "select  *  from   (select  distinct update_time from tb_weather where   city = #{city} " +
            "order by update_time desc limit 7 ) as t" +
            ")")
    List<ChartData> getHistoryWindPowder(String city);

    @Select("select SUBSTR(update_time,1,8) as name, pressure as value from  tb_weather" +
            " where city = #{city} and update_time in  (" +
            "select  *  from   (select  distinct update_time from tb_weather where   city = #{city} " +
            "order by update_time desc limit 7 ) as t" +
            ")")
    List<ChartData> getHistoryPressure(String city);

    @Select("select  max(update_time) from  tb_weather")
    String getCurrentDay();

    @Select("select province as name, avg(degree) as value from  tb_weather " +
            "group by province limit 15")
    List<ChartData> getProvinceWeather();

    @Select("select city as name, avg(degree) as value from  tb_weather " +
            "group by city limit 15")
    List<ChartData> getCityWeather();

    @Select("select county as name, avg(degree) as value from  tb_weather " +
            "group by county limit 15")
    List<ChartData> getCountyWeather();

    @Select("select * from tb_weather where city = #{city} and update_time like concat('%', SUBSTR(#{t},0,8), '%' ) limit 1")
    Weather getW(String t, String city);
}
