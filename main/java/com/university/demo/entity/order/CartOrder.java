package com.university.demo.entity.order;


import lombok.Data;
import java.util.List;

/**
 * @author max
 * @since  2022-12-22
 */

@Data
public class CartOrder extends Order {

    private List<Cart> cartList;

}
