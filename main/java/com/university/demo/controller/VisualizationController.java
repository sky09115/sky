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


    // 可视化分析1  ****************************
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

