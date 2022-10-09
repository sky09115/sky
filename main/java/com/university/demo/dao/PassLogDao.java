package com.university.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.university.demo.entity.Log;
import com.university.demo.entity.PassLog;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author redcomet
 * @since 2021-04-19
 */
public interface PassLogDao extends BaseMapper<PassLog> {

    @Select("select  count(*) from  tb_passlog where  user_id = #{id} and  create_time>DATE_SUB(CURDATE(), INTERVAL 6 MONTH) ")
    Integer countPass(Integer id);
}
