package com.university.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.university.demo.entity.Cart;
import com.university.demo.entity.Fav;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CartDao extends BaseMapper<Cart> {

}
