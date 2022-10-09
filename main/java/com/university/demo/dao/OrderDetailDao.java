package com.university.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.entity.Order;
import com.university.demo.entity.OrderDetail;
import com.university.demo.entity.response.ChartData;
import com.university.demo.entity.response.OrderVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author redcomet
 * @since
 */
public interface OrderDetailDao extends BaseMapper<OrderDetail> {

}
