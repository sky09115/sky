package com.university.demo.service.order;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.order.OrderDao;
import com.university.demo.entity.order.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements OrderService {
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
}
