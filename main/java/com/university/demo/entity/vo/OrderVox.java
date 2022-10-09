package com.university.demo.entity.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.university.demo.entity.Cart;
import com.university.demo.entity.Order;
import com.university.demo.entity.Product;
import com.university.demo.entity.response.OrderVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class OrderVox extends OrderVo {

    private List<Cart> products;

}
