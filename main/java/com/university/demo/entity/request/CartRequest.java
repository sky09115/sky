package com.university.demo.entity.request;

import lombok.Data;

/**
 * @author: 15760
 * @Date: 2022年7月14日10:07:59
 * @Descripe:
 */
@Data
public class CartRequest {
    private Integer productId;

    private Integer userId;

    private Integer num;
}
