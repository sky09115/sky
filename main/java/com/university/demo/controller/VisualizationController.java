package com.university.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.university.demo.dao.VisDao;
import com.university.demo.entity.response.ChartData;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.python.TransferPython.ToPython;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    ToPython toPython;

    // 主页面板     ***************************
    @GetMapping("/analysis")
    public ServerResponse analysis() throws Exception{
        List<ChartData> chartDataList = new ArrayList<>();
        chartDataList.add(dao.getC1());
        chartDataList.add(dao.getC2());
        chartDataList.add(dao.getC3());
        chartDataList.add(dao.getC4());
        chartDataList.add(dao.getC5());
        chartDataList.add(dao.getC6());
        chartDataList.add(dao.getC7());
        chartDataList.add(dao.getC8());
        return ServerResponse.ofSuccess(chartDataList);
    }

    // 可视化分析1  ****************************
    @GetMapping("/get11")
    public ServerResponse get11() throws Exception{
        List<ChartData> dataList = dao.get11();
        return ServerResponse.ofSuccess(dataList);
    }

    @GetMapping("/get13")
    public ServerResponse get13() throws Exception{
        List<ChartData> dataList = dao.get13();
        return ServerResponse.ofSuccess(dataList);
    }

    // 可视化分析3  ****************************
    @GetMapping("/get31")
    public ServerResponse get31() throws Exception{
        List<ChartData> dataList = dao.get31();
        return ServerResponse.ofSuccess(dataList);
    }

    @GetMapping("/get32")
    public ServerResponse get32() throws Exception{
        Map<String, Object> map = new HashMap();
        map.put("ratio1", dao.get32());
        return ServerResponse.ofSuccess(map);
    }

    @GetMapping("/get33")
    public ServerResponse get33() throws Exception{
        List<ChartData> dataList = dao.get33();
        return ServerResponse.ofSuccess(dataList);
    }
    // 词云        ****************************

    @RequestMapping(value = "/get51", method = RequestMethod.GET)
    public ServerResponse get51() throws ParseException {
        //调用python脚本
        String content = toPython.wordcloud(" ");
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

