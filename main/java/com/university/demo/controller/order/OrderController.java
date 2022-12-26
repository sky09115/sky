package com.university.demo.controller.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.controller.base.BaseController;
import com.university.demo.controller.base.MyWrapper;
import com.university.demo.dao.order.OrderDetailDao;
import com.university.demo.entity.User;
import com.university.demo.entity.order.Cart;
import com.university.demo.entity.order.CartOrder;
import com.university.demo.entity.order.Order;
import com.university.demo.entity.order.OrderDetail;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.service.UserService;
import com.university.demo.service.order.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author chenqi
 * @since  2022-12-22
 * @说明： 1.
 *        2.
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController<Order> {

    @Autowired
    UserService userService;
    @Autowired
    OrderDetailDao orderDetailDao;

    @Autowired
    CartService cartService;

    protected String[] search_fields = new String[]{"uid", "status", "goods_name" };
    protected String[] search_filter = new String[]{};

    @Override
    @GetMapping("/")
    public ServerResponse list(HttpServletRequest request,
                               @RequestParam(defaultValue = "") String search,
                               @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "15") Integer limit
                               ) {
        Page<Order> pages = new Page<>(page, limit);
        MyWrapper<Order> wrapperFactory = new MyWrapper<>();
        QueryWrapper<Order> wrapper = wrapperFactory.init(request, search, search_fields, search_filter);
        IPage<Order> iPage = baseSerivce.page(pages, wrapper);
        return ServerResponse.ofSuccess(iPage);
    }

    @PostMapping("/add")
    public ServerResponse add(@RequestBody CartOrder order) {
        Integer userId = order.getUid();
        User user = userService.getById(userId);
        order.setPhone(user.getPhone());
        boolean b = baseSerivce.save(order);
        if (b) {
            // 处理订单明细 Begin
            System.out.println("orderId:"+ order.getId());   // 自动回填
            List<Cart> products = order.getCartList();
            products.forEach(p->{
                OrderDetail od = new OrderDetail();
                od.setOid(order.getId());
                od.setGoods_logo(p.getGood_logo());
                od.setGoods_name(p.getGood_name());
                od.setAmount(p.getAmount());
                od.setPrice(p.getPrice());
                od.setUid(p.getUid());
                od.setIid(p.getIid());
                od.setStatus(Order.STAT_NOT_PAY);
                orderDetailDao.insert(od);
                // 处理购物车
                cartService.removeById(p);
            });
//            System.out.println(products);
            // 处理订单明细 end

            // 处理购物车 begin

            // 处理购物车 end
            return ServerResponse.ofSuccess("添加成功", order);
        }
        return ServerResponse.ofError("添加失败!");
    }
}

