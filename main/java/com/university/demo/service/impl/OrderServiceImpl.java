package com.university.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.OrderDao;
import com.university.demo.dao.OrderDetailDao;
import com.university.demo.entity.Log;
import com.university.demo.entity.Order;
import com.university.demo.entity.OrderDetail;
import com.university.demo.entity.response.OrderVo;
import com.university.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    public Page<OrderVo> selectPage(Page<OrderVo> page, String userName, Integer groupId, String type){
        return page.setRecords(baseMapper.select(page, userName, groupId, type));
    }

    public Page<OrderVo> selectAllPage(Page<OrderVo> page, String userName, String type){
        return page.setRecords(baseMapper.select2(page, userName, type));
    }

    public IPage<OrderVo> selectAllPage2(Page<OrderVo> page, Integer userId, String type){
        return baseMapper.select3(page, userId, type);
    }

    @Override
    public void pay(Integer userId, String orderId) {
        Order order = baseMapper.selectById(orderId);
        order.setStatus(Order.STAT_PAYED);
        baseMapper.updateById(order);
    }

    @Override
    public void cancel(Integer userId, String orderId) {
        Order order = baseMapper.selectById(orderId);
        order.setStatus(Order.STAT_CANCELED);
        baseMapper.updateById(order);
    }

    @Override
    public Double getAmountSum(Integer status) {
        return baseMapper.getAmountSum(status);
    }

    @Override
    public void updateDetail(String orderId, Integer status) {
        QueryWrapper<OrderDetail> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("oid", orderId);
        List<OrderDetail> orderDetailList = orderDetailDao.selectList(wrapper2);

        orderDetailList.forEach(od->{
            od.setStatus(status);
            orderDetailDao.updateById(od);
        });
    }

    @Override
    public List<Integer> chartCount() throws ParseException {
        List<Integer> values = new ArrayList<>();
        List<String> days = orderDao.orderDay();
        for(String _day : days){
            QueryWrapper<Order> query = new QueryWrapper<>();
            query.like("create_time",_day);
            values.add(orderDao.selectCount(query));
        }
        return values;
    }

    @Override
    public List<String> chartDay() throws ParseException {
        return  baseMapper.orderDay();
    }
}
