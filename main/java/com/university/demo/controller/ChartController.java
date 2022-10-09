package com.university.demo.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.dao.OrderDao;
import com.university.demo.dao.ProductDao;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.entity.system.SysConstant;
import com.university.demo.entity.MapData;
import com.university.demo.entity.response.*;
import com.university.demo.service.*;
import com.university.demo.service.impl.TokenService;
import com.university.demo.util.spark.SparkUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

/**
 * @author redcomet
 * @since 2021-04-03
 */
@RestController
@RequestMapping("/chart")
public class ChartController {

    @Autowired
    private MapDataService mapDataService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private PicService picService;
    @Autowired
    private LogService logService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ThumbService thumbService;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductService productService;

    @Autowired
    private SongService songService;
//    @Autowired
//    private SparkUtils sparkUtils;

    @GetMapping({"/map/search/{keyword}","/map/search/"})
    public ServerResponse search(@PathVariable(value = "keyword",required = false) String keyword  ) {
        QueryWrapper<MapData> wrapper = new QueryWrapper<>();
        wrapper.like(!StringUtils.isEmpty(keyword), "keyword", keyword)
                .eq("deleted",false);
        List<MapData> datas = mapDataService.list(wrapper);
        List<MapDataVo> vos = new ArrayList<>();
        datas.forEach(mapData -> {
            MapDataVo vo = new MapDataVo();
            vo.setName(mapData.getCity());
            vo.setValue(mapData.getAmount());
            vos.add(vo);
        });
        if (vos != null) {
            return ServerResponse.ofSuccess(vos);
        }
        return ServerResponse.ofError("查询不到数据!");
    }

    @GetMapping("/panel")
    public ServerResponse panelData() {
        Map map = new HashMap();
        map.put("users", userService.count());
        map.put("logs", logService.count());
        map.put("three", songService.count());
        map.put("four", orderService.getAmountSum(5));

//        map.put("four",schoolService.count());
//        map.put("schools",schoolService.count());
//        map.put("income",gkScoreService.count());
        return ServerResponse.ofSuccess(map);
    }

    @GetMapping("/loginData")
    public ServerResponse loginData() throws ParseException {

        Map map = new HashMap();
        map.put("xData",logService.chartDay(SysConstant.LOGIN));
        map.put("expectedData",logService.chartCount(SysConstant.LOGIN));
        return ServerResponse.ofSuccess(map);
    }

    @GetMapping("/userGroupData")
    public ServerResponse userGroupData() throws ParseException {
        Map map = new HashMap();
        map.put("userGroupData",groupService.groupUsers());
        return ServerResponse.ofSuccess(map);
    }



    /* 大屏数据 */
    // 评论和点赞 双柱图
    @GetMapping("/commentnThumbData")
    public ServerResponse commentnThumbData() throws ParseException {
        Map map = new HashMap();
        map.put("commentData",commentService.getChartData());
        map.put("thumbData",thumbService.getChartData());
        return ServerResponse.ofSuccess(map);
    }

    // 思南图
    @GetMapping("/sinan")
    public ServerResponse sinanData() throws ParseException {
        Map map = new HashMap();
        map.put("cdata",logService.chartData("GROUP_MAN"));
        return ServerResponse.ofSuccess(map);
    }



    // 金字塔图
    @GetMapping("/jzt")
    public ServerResponse jinzitaData() throws ParseException {
        Map map = new HashMap();
        map.put("cdata",logService.chartData("ROLE_MAN"));
        return ServerResponse.ofSuccess(map);
    }

//    @GetMapping("/distribute")
//    public ServerResponse distribute() throws ParseException {
//        Map map = new HashMap();
//        map.put("distribution",schoolService.getDistributeByProvince());
//        return ServerResponse.ofSuccess(map);
//    }
//
//    @GetMapping("/china")
//    public ServerResponse china() throws ParseException {
//        Map map = new HashMap();
//        List<List<PieData>> list = new ArrayList<>();
//        List<Wish> wishes = wishService.list();
//        for(int i=0;i<wishes.size();i++){
//            List<PieData> p = new ArrayList<>();
//            Wish wish = wishes.get(i);
//            School school = schoolService.getSchoolBySid(wish.getRec1());
//            PieData pieData0 = new PieData();
//            PieData pieData = new PieData();
//            if(school==null || school.getCityName()==null || org.springframework.util.StringUtils.isEmpty(school.getCityName()))
//                continue;
//            pieData0.setName("北京");
//            pieData0.setValue(100);
//            p.add(pieData0);
//            pieData.setName(school.getCityName());
//            pieData.setValue(100);
//            p.add(pieData);
//
//            list.add(p);
//        }
//        map.put("china",list);
//        return ServerResponse.ofSuccess(map);
//    }

    @GetMapping("/basic")
    public ServerResponse basic() {
        Map map = new HashMap();
//        map.put("c1",sparkUtils.count("goods_list"));
//        map.put("c2",sparkUtils.count("tb_order"));
        map.put("c3",orderDao.getCount(1));
        map.put("c4",orderDao.getCount(3));
        map.put("c5",orderDao.getCount(4));
        map.put("c6",orderDao.getCount(5));
        map.put("c7",orderService.getAmountSum(5));
        map.put("c8",orderDao.getNotAmountSum(4));

        map.put("m1",orderDao.getBrand());
        map.put("m2",orderDao.getCategory());
        return ServerResponse.ofSuccess(map);
    }

    @GetMapping("/list")
    public ServerResponse list() {
        Map map = new HashMap();
        map.put("sale",orderDao.getSale());
        return ServerResponse.ofSuccess(map);
    }

    @GetMapping("/monthSales")
    public ServerResponse monthSales() {
        List<ChartData> sales = new ArrayList<>();
        List<ChartData> prices = new ArrayList<>();

        String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};
        for(String m: months){
            Integer count = orderDao.getSaleByMonth(m);
            ChartData chartData = new ChartData();
            chartData.setName(m);
            if(count == null)
                chartData.setValue(0);
            else
                chartData.setValue(count);
            sales.add(chartData);

            Integer count2 = orderDao.getPriceByMonth(m);
            ChartData chartData2 = new ChartData();
            chartData2.setName(m);
            if(count == null)
                chartData2.setValue(0);
            else
                chartData2.setValue(count2);
            prices.add(chartData2);
        }

        Map map = new HashMap();
        map.put("sale", sales);
        map.put("prices", prices);
        return ServerResponse.ofSuccess(map);
    }
}

