package com.university.demo.controller;



import com.alibaba.fastjson.JSONObject;
import com.university.demo.dao.UserDao;

import com.university.demo.dao.VisDao;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.entity.system.SysConstant;
import com.university.demo.python.TransferPython.ToPython;
import com.university.demo.service.LogService;
import com.university.demo.service.UserService;
import com.university.demo.service.game.GameServerService;
import com.university.demo.service.game.GameService;
import com.university.demo.service.movie2.MovieDetailService;
import com.university.demo.service.movie2.MovieReviewsService;
import com.university.demo.service.movie2.MovieUserRatingsService;
import com.university.demo.service.movie2.MovieUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author redcomet
 * @since  2022年12月09日
 */
@RestController
@RequestMapping("/chart")
public class ChartController {
    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;

    @Autowired
    private MovieDetailService movieDetailService;

    @Autowired
    private MovieUserRatingsService movieUserRatingsService;
    @Autowired
    private GameService gameService;
    @Autowired
    private GameServerService gameServerService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private VisDao visDao;

    @Autowired
    ToPython toPython;
    @GetMapping("/panel")
    public ServerResponse panelData() {
        Map map = new HashMap();
        map.put("users", userService.count());
        map.put("logs", logService.count());
        map.put("three", gameService.count());
        map.put("four", gameServerService.count());
        return ServerResponse.ofSuccess(map);
    }

    @GetMapping("/loginData")
    public ServerResponse loginData() throws ParseException {
        Map map = new HashMap();
        map.put("xData",logService.chartDay(SysConstant.LOGIN));
        map.put("expectedData",logService.chartCount(SysConstant.LOGIN));
        return ServerResponse.ofSuccess(map);
    }

    @GetMapping("/userJob")
    public ServerResponse userAddr() throws ParseException {
        Map map = new HashMap();
        map.put("userAddr",userDao.getUserJob());
        return ServerResponse.ofSuccess(map);
    }

    @GetMapping("/userJobSex")
    public ServerResponse userAddrSex() throws ParseException {
        Map map = new HashMap();
        map.put("userAddr",userDao.getUserJob());
        List<Integer> males = new ArrayList<>();
        List<Integer> females = new ArrayList<>();
        userDao.getUserJob().forEach(i->{
            String addr = i.getName();
            males.add(userDao.getUserJobSex("M", addr));
            females.add(userDao.getUserJobSex("F", addr));
        });

        map.put("male",males);
        map.put("female",females);
        return ServerResponse.ofSuccess(map);
    }
    @GetMapping("/get11")
    public ServerResponse get11() throws ParseException {
        Map map = new HashMap();
        map.put("onlineNum",movieDetailService.count(null));
//        map.put("totalNum",movieUserService.count(null));
//        map.put("offlineNum",movieReviewsService.count(null));
        map.put("alarmNum",movieUserRatingsService.count(null));
        return ServerResponse.ofSuccess(map);
    }
    @GetMapping("/get12")
    public ServerResponse get12() throws ParseException {
        Map map = new HashMap();
        map.put("province",userDao.getUserProvince());
        return ServerResponse.ofSuccess(map);
    }

    @GetMapping("/get13")
    public ServerResponse get13() throws ParseException {
        Map map = new HashMap();
        map.put("dateList",visDao.review_day());
        map.put("numList",logService.reviewCount());
        return ServerResponse.ofSuccess(map);
    }

    @GetMapping("/get23")
    public ServerResponse get23() throws ParseException {
        Map map = new HashMap();
        map.put("data",visDao.get23());
        return ServerResponse.ofSuccess(map);
    }

    @RequestMapping(value = "/get31", method = RequestMethod.GET)
    public ServerResponse get31() throws ParseException {
        //调用python脚本
        String content = toPython.wordcloud2(" ");
        //转为json数据
        JSONObject jo = JSONObject.parseObject(content);
        return ServerResponse.ofSuccess(jo);
    }


    @GetMapping("/get32")
    public ServerResponse get32() throws ParseException {
        Map map = new HashMap();
        String[] category = {"美国", "中国大陆","中国香港", "日本", "法国", "英国", "韩国", "德国"};
        map.put("category", category);
//        map.put("barData", movieReviewsService.getRate1(category));
//        map.put("lineData", movieReviewsService.getRateNum(category));
//        map.put("rateData", movieReviewsService.getRate5Star(category));
        return ServerResponse.ofSuccess(map);
    }
}

