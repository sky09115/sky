package com.university.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.university.demo.entity.Group;
import com.university.demo.entity.Log;
import com.university.demo.entity.response.PieData;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author redcomet
 * @since 2021-04-17
 */
public interface GroupDao extends BaseMapper<Group> {

    @Select("select  a.remark as name,count(b.id) as value from  tb_group a, tb_user b where b.group_id = a.id group by a.remark")
    List<PieData> groupUsers();
}
