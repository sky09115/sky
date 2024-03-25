package com.university.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.university.demo.dao.VisDao;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.entity.system.SysConstant;
import com.university.demo.entity.traffic.Info;
import com.university.demo.python.TransferPython.ToPython;
import com.university.demo.service.traffic.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tesla
 * @since 2022-10-08
 */
@RestController
@RequestMapping("/vis2")
public class VisualizationController {


    @Autowired
    private VisDao dao;

    @Autowired
    private InfoService infoService;

    @Autowired
    ToPython toPython;

    // 主页面板     ***************************
    @RequestMapping(value = "/chart1", method = RequestMethod.GET)
    public ServerResponse chart1() throws ParseException {
        Map map = new HashMap();
        map.put("data",dao.chart1());
        return ServerResponse.ofSuccess(map);
    }

    @RequestMapping(value = "/chart2", method = RequestMethod.GET)
    public ServerResponse chart2() throws ParseException {
        Map map = new HashMap();
        map.put("data",dao.chart2());
        return ServerResponse.ofSuccess(map);
    }

    @RequestMapping(value = "/chart3", method = RequestMethod.GET)
    public ServerResponse chart3() throws ParseException {
        Map map = new HashMap();
        map.put("data",dao.chart3());
        return ServerResponse.ofSuccess(map);
    }
    @RequestMapping(value = "/dash0", method = RequestMethod.GET)
    public ServerResponse dash0() throws ParseException {
        Map map = new HashMap();
        map.put("data1",dao.dash00());
        map.put("data2",dao.dash01());
        map.put("data3",dao.dash02());
        return ServerResponse.ofSuccess(map);
    }

    @RequestMapping(value = "/dash1", method = RequestMethod.GET)
    public ServerResponse dash1() throws ParseException {
        Map map = new HashMap();
        map.put("data",dao.dash1());
        return ServerResponse.ofSuccess(map);
    }

    @RequestMapping(value = "/dash2", method = RequestMethod.GET)
    public ServerResponse dash2() throws ParseException {
        Map map = new HashMap();
        map.put("data",dao.dash2());
        return ServerResponse.ofSuccess(map);
    }

    @RequestMapping(value = "/dash3", method = RequestMethod.GET)
    public ServerResponse dash3() throws ParseException {
        Map map = new HashMap();
        map.put("data",dao.dash3());
        return ServerResponse.ofSuccess(map);
    }

    @RequestMapping(value = "/dash4", method = RequestMethod.GET)
    public ServerResponse dash4() throws ParseException {
        Map map = new HashMap();
        map.put("data",dao.dash4());
        return ServerResponse.ofSuccess(map);
    }
    @RequestMapping(value = "/dash5", method = RequestMethod.GET)
    public ServerResponse dash5() throws ParseException {
        Map map = new HashMap();
        map.put("data",dao.dash5());
        return ServerResponse.ofSuccess(map);
    }
    @RequestMapping(value = "/dash6", method = RequestMethod.GET)
    public ServerResponse dash6() throws ParseException {
        Map map = new HashMap();
        map.put("data",dao.dash6());
        return ServerResponse.ofSuccess(map);
    }

    @RequestMapping(value = "/predict", method = RequestMethod.GET)
    public ServerResponse predict(@RequestParam(defaultValue = "") String lm) throws ParseException {
        //调用python脚本
        String content = toPython.wordcloud("");
        //转为json数据
        JSONObject jo = JSONObject.parseObject(content);
        return ServerResponse.ofSuccess(jo);
    }

    @RequestMapping(value = "/get41", method = RequestMethod.GET)
    public ServerResponse get41() throws ParseException {
        //调用python脚本
        String content = toPython.wordcloud2(" ");
        //转为json数据
        JSONObject jo = JSONObject.parseObject(content);
        return ServerResponse.ofSuccess(jo);
    }
}

