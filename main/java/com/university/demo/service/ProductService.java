package com.university.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.university.demo.entity.Product;

public interface ProductService extends IService<Product> {

    void updatePrice(Integer goodsId, String price);

    Double getPrice(Integer goodsId);
}
