package com.university.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.dao.ProductDao;
import com.university.demo.entity.*;
import com.university.demo.entity.request.CartRequest;
import com.university.demo.entity.request.ProductRequest;
import com.university.demo.entity.request.SearchRequest;
import com.university.demo.entity.response.NewsVo;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.python.TransferPython.ToPython;
import com.university.demo.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.CacheRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品控制器
 * @author
 * @since 2022年7月13日
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CollectService collectService;

    @Autowired
    private RateService rateService;

    @Autowired
    private ProductDao productDao;
    @Autowired
    ToPython toPython;
    @PostMapping("/modify")
    public ServerResponse modify(@RequestBody ProductVo product) {
//        productService.updatePrice(product.getId(), product.getPrice());
        return productService.updateById(product) ? ServerResponse.ofSuccess("更新成功！") : ServerResponse.ofError("更新失败！");
    }

    @GetMapping("/delete/{id}")
    public ServerResponse delete(@PathVariable("id") Integer id) {
        return productService.removeById(id) ? ServerResponse.ofSuccess("删除成功！") : ServerResponse.ofError("删除失败！");
    }

    @GetMapping("/records/{page}")
    public ServerResponse querys(@PathVariable("page") Integer page,
                                       @RequestParam(defaultValue = "10") Integer limit) {
        Page<Product> pages = new Page<>(page, limit);
        QueryWrapper<Product> wrapper = new QueryWrapper<Product>();
        IPage<Product> iPage = productService.page(pages, wrapper);
        return ServerResponse.ofSuccess(iPage);
    }

    @GetMapping({"/search/{keyword}","/search/"})
    public ServerResponse search(@PathVariable(value = "keyword",required = false) String keyword, @RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer limit) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.like(!StringUtils.isEmpty(keyword), "goods_name", keyword);
        Page<Product> pages = new Page<>(page, limit);
        IPage<Product> iPage = productService.page(pages, wrapper);
        if (page != null) {
            return ServerResponse.ofSuccess(iPage);
        }
        return ServerResponse.ofError("查询不到数据!");
    }

    @PostMapping("/search")
    public ServerResponse search2(@RequestBody SearchRequest params,
                                  @RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer limit) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.like(!StringUtils.isEmpty(params.getKeyword()), "goods_name", params.getKeyword());
        Page<Product> pages = new Page<>(page, limit);
        IPage<Product> iPage = productService.page(pages, wrapper);
        if (page != null) {
            return ServerResponse.ofSuccess(iPage);
        }
        return ServerResponse.ofError("查询不到数据!");
    }

    @PostMapping("/add")
    public ServerResponse add(@RequestBody ProductVo product) {
        boolean b = productService.save(product);
//        productService.updatePrice(product.getId(), product.getPrice());
        if (b) {
            return ServerResponse.ofSuccess("添加成功", product);
        }
        return ServerResponse.ofError("添加失败!");
    }

    /*********************     电商网站前端   (2022年7月13日09:54:32)   *************************/

    // 获取价格
    @PostMapping("/getPrice")
    public ServerResponse getPrice(@RequestBody Product product) {
        Map map = new HashMap();
        map.put("price", productService.getPrice(product.getId()));

        return ServerResponse.ofSuccess(map);
    }

    // 获取类别
    @PostMapping("/getCategory")
    public ServerResponse getCategory(@RequestBody Product product) {
        Map map = new HashMap();
        map.put("category", categoryService.list());
        return ServerResponse.ofSuccess(map);
    }

    // 根据分类获取商品数据，如果没有分类ID，则是全部的
    // 2022年7月13日 先这么处理下，后面可能要拓展字段
    @PostMapping({"/getAllProduct","/getProductByCategory"})
    public ServerResponse getProductByCategory(@RequestBody ProductRequest param) {
        Integer cid = 0;
        if (param.getCategoryID().length>0)
            cid = param.getCategoryID()[0];
//        for (int i=0;i<param.getCategoryID().length;i++)
//            System.out.println("categoryId:" + param.getCategoryID()[i]);
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.like(cid!=0, "gcat_id", cid);
        Page<Product> pages = new Page<>(param.getCurrentPage(), param.getPageSize());
        IPage<Product> iPage = productService.page(pages, wrapper);
        List<Product> products = iPage.getRecords();
        List<ProductVo> productVos = new ArrayList<>();
        products.forEach(p->{
            ProductVo pVo = new ProductVo();
            BeanUtils.copyProperties(p, pVo);
//            pVo.setPrice(String.valueOf(productDao.getPrice(p.getId())));
            productVos.add(pVo);
        });

        IPage<ProductVo> voIPage = new Page<>();
        voIPage.setRecords(productVos);
        voIPage.setSize(iPage.getSize());
        voIPage.setPages(iPage.getPages());
        voIPage.setCurrent(iPage.getCurrent());
        voIPage.setTotal(iPage.getTotal());

        if (pages != null) {
            return ServerResponse.ofSuccess(voIPage);
        }
        return ServerResponse.ofSuccess("查询不到数据!");
    }

    @PostMapping("/getProductBySearch")
    public ServerResponse getProductBySearch(@RequestBody ProductRequest param) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.like( param.getSearch()!=null, "goods_name", param.getSearch());
        Page<Product> pages = new Page<>(param.getCurrentPage(), param.getPageSize());
        IPage<Product> iPage = productService.page(pages, wrapper);
        List<Product> products = iPage.getRecords();
        List<ProductVo> productVos = new ArrayList<>();
        products.forEach(p->{
            ProductVo pVo = new ProductVo();
            BeanUtils.copyProperties(p, pVo);
//            pVo.setPrice(String.valueOf(productDao.getPrice(p.getId())));
            productVos.add(pVo);
        });

        IPage<ProductVo> voIPage = new Page<>();
        voIPage.setRecords(productVos);
        voIPage.setSize(iPage.getSize());
        voIPage.setPages(iPage.getPages());
        voIPage.setCurrent(iPage.getCurrent());
        voIPage.setTotal(iPage.getTotal());

        if (pages != null) {
            return ServerResponse.ofSuccess(voIPage);
        }
        return ServerResponse.ofSuccess("查询不到数据!");
    }

    // 按照分类获取数据
    @PostMapping("/getPromoProduct")
    public ServerResponse getPromoProduct(@RequestBody ProductRequest param) {
        Integer cid = param.getCategoryName();
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.like(cid!=0, "gcat_id", cid);
        Page<Product> pages = new Page<>(1, 7);
        IPage<Product> iPage = productService.page(pages, wrapper);
        List<Product> products = iPage.getRecords();
        List<ProductVo> productVos = new ArrayList<>();


        IPage<ProductVo> voIPage = new Page<>();
        voIPage.setRecords(productVos);
        voIPage.setSize(iPage.getSize());
        voIPage.setPages(iPage.getPages());
        voIPage.setCurrent(iPage.getCurrent());
        voIPage.setTotal(iPage.getTotal());

        if (pages != null) {
            return ServerResponse.ofSuccess(voIPage);
        }
        return ServerResponse.ofSuccess("查询不到数据!");
    }

    // 获取推荐商品数据
    @PostMapping("/getRec1")
    public ServerResponse getRec1(@RequestBody SearchRequest query) {
        List<ProductVo> records = new ArrayList();
        List<RecommendedItem> items = rateService.getRecommendItemIds(query.getUserId(), 7);

        for (RecommendedItem item : items) {
            ProductVo vo = new ProductVo();
            Product product = productService.getById(item.getItemID());
            BeanUtils.copyProperties(product, vo);
            records.add(vo);
        }

        return ServerResponse.ofSuccess(records);
    }

    // 获取推荐商品数据2
    @PostMapping("/getRec2")
    public ServerResponse getRec2(@RequestBody SearchRequest query) {
        List<ProductVo> records = new ArrayList();
        String content = toPython.itemrec(String.valueOf(query.getUserId()));
        //转为json数据
        JSONArray jo = JSONObject.parseArray(content);
        for(int i=0;i<jo.size();i++){
            JSONObject  obj = jo.getJSONObject(i);

            ProductVo vo = new ProductVo();
            Product product = productService.getById(obj.getInteger("iid"));
            BeanUtils.copyProperties(product, vo);
            records.add(vo);
        }

        return ServerResponse.ofSuccess(records);
    }

    /**
     * 多层感知机，神经网络混合CF算法
     * @param query
     * @return
     */
    @PostMapping("/getRec3")
    public ServerResponse getRec3(@RequestBody SearchRequest query) {
        List<ProductVo> records = new ArrayList();
        String content = toPython.mlp(String.valueOf(query.getUserId()));
        //转为json数据
        JSONArray jo = JSONObject.parseArray(content);
        for(int i=0;i<jo.size();i++){
            JSONObject  obj = jo.getJSONObject(i);

            ProductVo vo = new ProductVo();
            Product product = productService.getById(obj.getInteger("iid"));
            BeanUtils.copyProperties(product, vo);
            records.add(vo);
        }

        return ServerResponse.ofSuccess(records);
    }

    // 获取商品详情,  // 获取商品详情图片
    @PostMapping({"/getDetails", "/getDetailsPicture"})
    public ServerResponse getDetails(@RequestBody Product product) {
        Map map = new HashMap();
        ProductVo pVo = new ProductVo();
        BeanUtils.copyProperties(productService.getById(product.getId()) , pVo);
        map.put("product", pVo);
        return ServerResponse.ofSuccess(map);
    }

    // 添加购物车
    @PostMapping("/shoppingCart/addShoppingCart")
    public ServerResponse addShoppingCart(@RequestBody CartRequest cartRequest) {
        Map map = new HashMap();
        // 先查询下购物车里是否已经存在这个商品了？
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq( "user_id", cartRequest.getUserId());
        wrapper.eq( "goods_id", cartRequest.getProductId());
        List<Cart> carts = cartService.list(wrapper);
        if(carts.size()>0){
            Cart cart = carts.get(0);
            int amount = cart.getNum()+1;
            cart.setNum(amount);
            cartService.updateById(cart);
            map.put("code", "002");
        }else{
            Cart cart = new Cart();
            cart.setGoodsId(cartRequest.getProductId());
            Product good = productService.getById(cartRequest.getProductId());
            cart.setGoodsLogo(good.getGoodsLogo());
            cart.setGoodsName(good.getGoodsName());
            cart.setUserId(cartRequest.getUserId());
            try {
                cart.setPrice(Double.valueOf(good.getPrice()));
            }
            catch (Exception e){
                cart.setPrice(100.0);
            }

            cart.setNum(1);
            cartService.save(cart);
            map.put("cart", cart);
            map.put("code", "001");
        }
        return ServerResponse.ofSuccess(map);
    }

    // 读取购物车
    @PostMapping("/shoppingCart/getShoppingCart")
    public ServerResponse getShoppingCart(@RequestBody CartRequest cartRequest) {
        Map map = new HashMap();
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq( "user_id", cartRequest.getUserId()).eq("deleted", false);
        map.put("shoppingCartData", cartService.list(wrapper));
        return ServerResponse.ofSuccess(map);
    }

    // 删除购物车
    @PostMapping("/shoppingCart/deleteShoppingCart")
    public ServerResponse deleteShoppingCart(@RequestBody CartRequest cartRequest) {
        Map map = new HashMap();
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq( "user_id", cartRequest.getUserId());
        wrapper.eq( "goods_id", cartRequest.getProductId());
        cartService.remove(wrapper);
        map.put("message", "删除成功");
        return ServerResponse.ofSuccess(map);
    }

    // 更新购物车
    @PostMapping("/shoppingCart/updateShoppingCart")
    public ServerResponse updateShoppingCart(@RequestBody CartRequest cartRequest) {
        Map map = new HashMap();
        Cart c = new Cart();
        c.setNum(cartRequest.getNum());
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq( "user_id", cartRequest.getUserId());
        wrapper.eq( "goods_id", cartRequest.getProductId());
        cartService.update(c, wrapper);
        map.put("message", "更新成功");
        return ServerResponse.ofSuccess(map);
    }

    // 添加收藏
    @PostMapping("/collect/addCollect")
    public ServerResponse addCollect(@RequestBody CartRequest cartRequest) {
        Map map = new HashMap();
        // 先查询下购物车里是否已经存在这个商品了？
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.eq( "user_id", cartRequest.getUserId());
        wrapper.eq( "product_id", cartRequest.getProductId());
        wrapper.eq("deleted", false);
        List<Collect> collects = collectService.list(wrapper);
        if(collects.size()>0){
            Collect collect = collects.get(0);
            collectService.updateById(collect);
        }else{
            Collect collect = new Collect();
            collect.setProductId(cartRequest.getProductId());
            collect.setUserId(cartRequest.getUserId());
            collectService.save(collect);
            map.put("collect", collect);
        }
        return ServerResponse.ofSuccess(map);
    }

    // 删除收藏
    @PostMapping("/collect/deleteCollect")
    public ServerResponse deleteCollect(@RequestBody CartRequest cartRequest) {
        Map map = new HashMap();
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.eq( "user_id", cartRequest.getUserId());
        wrapper.eq( "product_id", cartRequest.getProductId());

        collectService.remove(wrapper);
        map.put("message", "删除成功");
        return ServerResponse.ofSuccess(map);
    }

    // 获取收藏列表
    @PostMapping("/collect/getCollect")
    public ServerResponse getCollect(@RequestBody CartRequest cartRequest) {
        Map map = new HashMap();
        Integer userId = cartRequest.getUserId();
        List<Product> products = productDao.getColloectProducts(userId);
        List<ProductVo> productVos = new ArrayList<>();
        products.forEach(p->{
            ProductVo pVo = new ProductVo();
            BeanUtils.copyProperties(p, pVo);
//            pVo.setPrice(String.valueOf(productDao.getPrice(p.getId())));
            productVos.add(pVo);
        });
        map.put("products", productVos);
        return ServerResponse.ofSuccess(map);
    }
}

