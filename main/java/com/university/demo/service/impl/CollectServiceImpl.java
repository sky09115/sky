package com.university.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.CartDao;
import com.university.demo.dao.CollectDao;
import com.university.demo.entity.Cart;
import com.university.demo.entity.Collect;
import com.university.demo.service.CartService;
import com.university.demo.service.CollectService;
import org.springframework.stereotype.Service;


@Service
public class CollectServiceImpl extends ServiceImpl<CollectDao, Collect> implements CollectService {

}
