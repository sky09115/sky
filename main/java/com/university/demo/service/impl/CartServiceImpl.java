package com.university.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.CartDao;
import com.university.demo.entity.Cart;
import com.university.demo.service.CartService;
import org.springframework.stereotype.Service;


@Service
public class CartServiceImpl extends ServiceImpl<CartDao, Cart> implements CartService {

}
