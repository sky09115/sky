package com.university.demo.service.order;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.order.CartDao;
import com.university.demo.entity.order.Cart;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl extends ServiceImpl<CartDao, Cart> implements CartService {
}
