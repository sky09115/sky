package com.university.demo.dao.traffic;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.entity.traffic.Road;
import com.university.demo.entity.traffic.RoadVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author
 * @since 2023-01-29 
 */
public interface RoadDao extends BaseMapper<Road> {
    @Select("select  a.*,b.lm as lm from tb_road a,tb_info b " +
            " where a.blockid = b.id " +
            " and ( b.lm like CONCAT('%',#{keyword},'%') " +
            "  ) ")
    List<RoadVo> select(Page page, String keyword);
}
