package com.university.demo.entity.response;

import com.university.demo.entity.Order;
import com.university.demo.entity.OrderDetail;
import lombok.Data;

import java.util.List;

@Data
public class OrderVo extends Order {

    private String realname;

    private String deptname;

    private List<OrderDetail> details;
}
