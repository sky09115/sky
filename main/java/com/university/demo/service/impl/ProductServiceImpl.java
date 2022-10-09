package com.university.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.PriceDao;
import com.university.demo.dao.ProductDao;
import com.university.demo.entity.Price;
import com.university.demo.entity.Product;
import com.university.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductDao, Product> implements ProductService {

    @Autowired
    PriceDao priceDao;

    @Override
    public void updatePrice(Integer goodsId, String price) {
        QueryWrapper<Price> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_id", goodsId).orderByDesc("upd_time");
        List<Price> list = priceDao.selectList(wrapper);
        if(list.size()==0){
            Price p = new Price();
            p.setGoodsId(goodsId);
            p.setPriceDown(price);
            p.setUpdTime(LocalDateTime.now());
            priceDao.insert(p);
        }else{
            Price newest = list.get(0);
            if(!newest.getPriceDown().equals(price)){
                Price p = new Price();
                p.setGoodsId(goodsId);
                p.setPriceDown(price);
                p.setUpdTime(LocalDateTime.now());
                priceDao.insert(p);
            }
        }
    }

    @Override
    public Double getPrice(Integer goodsId) {
        QueryWrapper<Price> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_id", goodsId).orderByDesc("upd_time");
        Double price = 0.0;
        List<Price> list = priceDao.selectList(wrapper);
        if(list.size()!=0){
           price = Double.valueOf(list.get(0).getPriceDown());
        }

        return price;
    }
}
