package com.university.demo.dao.traffic;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.entity.traffic.Info;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author
 * @since 2023-01-29
 */
public interface InfoDao extends BaseMapper<Info> {
    @Select("select  * from  tb_info a where lm like CONCAT('%',#{keyword},'%')  and " +
            " exists (select  *  from  tb_road b where a.id = b.blockid )  ")
    List<Info> select(Page page, String keyword);
}
