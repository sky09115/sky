package com.university.demo.controller.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.controller.base.BaseController;
import com.university.demo.controller.base.MyWrapper;
import com.university.demo.entity.order.Cart;
import com.university.demo.entity.system.ServerResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenqi
 * @since  2022-12-22
 * @说明： 1.
 *        2.
 */
@RestController
@RequestMapping("/cart")
public class CartController extends BaseController<Cart> {

    protected String[] search_fields = new String[]{"uid", "iid", "status" };
    protected String[] search_filter = new String[]{};

    @Override
    @GetMapping("/")
    public ServerResponse list(HttpServletRequest request,
                               @RequestParam(defaultValue = "") String search,
                               @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "15") Integer limit
                               ) {
        Page<Cart> pages = new Page<>(page, limit);
        MyWrapper<Cart> wrapperFactory = new MyWrapper<>();
        QueryWrapper<Cart> wrapper = wrapperFactory.init(request, search, search_fields, search_filter);
        IPage<Cart> iPage = baseSerivce.page(pages, wrapper);
        return ServerResponse.ofSuccess(iPage);
    }
}

