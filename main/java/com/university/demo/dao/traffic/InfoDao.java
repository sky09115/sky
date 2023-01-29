package com.university.demo.dao.traffic;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.university.demo.entity.game.Game;
import com.university.demo.entity.response.ChartData;
import com.university.demo.entity.traffic.Info;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author
 * @since 2023-01-29
 */
public interface InfoDao extends BaseMapper<Info> {

}
