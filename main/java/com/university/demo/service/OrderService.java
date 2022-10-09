package com.university.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.university.demo.entity.Order;
import com.university.demo.entity.response.OrderVo;

import java.text.ParseException;
import java.util.List;

public interface OrderService extends IService<Order> {
    Page<OrderVo> selectPage(Page<OrderVo> page, String userName, Integer groupId, String type);

    Page<OrderVo> selectAllPage(Page<OrderVo> page, String userName, String type);

    IPage<OrderVo> selectAllPage2(Page<OrderVo> page, Integer userId, String type);

    void pay(Integer userId, String orderId);

    void cancel(Integer userId, String orderId);

    Double getAmountSum(Integer status);

    void updateDetail(String orderId, Integer status);

    List<Integer> chartCount() throws ParseException;
    List<String> chartDay() throws ParseException;
}
