package com.university.demo.controller.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.controller.base.BaseController;
import com.university.demo.controller.base.MyWrapper;
import com.university.demo.entity.game.Game;
import com.university.demo.entity.game.GameGood;
import com.university.demo.entity.order.Cart;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.service.game.GameGoodService;
import com.university.demo.service.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenqi
 * @since  2022-12-22
 * @说明： 1.
 *        2.
 */
@RestController
@RequestMapping("/cart")
public class CartController extends BaseController<Cart> {

    @Autowired
    GameGoodService gameGoodService;

    @Autowired
    GameService gameService;


    protected String[] search_fields = new String[]{"iid" };
    protected String[] search_filter = new String[]{"uid"};

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

    @GetMapping("/addShoppingCart")
    public ServerResponse addShoppingCart(@RequestParam(defaultValue = "1") Integer uid,
                               @RequestParam(defaultValue = "1") Integer iid) {
        Map map = new HashMap();
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid).eq("iid", iid);
        List<Cart> shoppingCart = baseSerivce.list(wrapper);
        if(shoppingCart.size()>0) {
            Cart cart = shoppingCart.get(0);
            Integer amount = cart.getAmount();
            cart.setAmount(amount+1);
            baseSerivce.updateById(cart);
            map.put("code", "002");  //已经存在了此件商品了
        }
        else {
            GameGood good = gameGoodService.getById(iid);
            QueryWrapper<Game> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("gameid", good.getGameid());
            Game game = gameService.getOne(wrapper1);
            Cart cart = new Cart();
            cart.setUid(uid);
            cart.setIid(iid);
            cart.setGood_name(good.getName());
            cart.setGood_logo(game.getShopimageurl());
            cart.setPrice(good.getPrice());
            cart.setDiscount(1.0);
            cart.setAmount(1);
            cart.setFprice(good.getPrice());
            baseSerivce.save(cart);
            map.put("code", "001");  //新加入购物车的商品,需要把新加入的商品返回
            map.put("cart", cart);
        }
        return ServerResponse.ofSuccess(map);
    }
}

