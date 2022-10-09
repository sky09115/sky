package com.university.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.university.demo.entity.Price;
import com.university.demo.entity.Product;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PriceDao extends BaseMapper<Price> {

}
