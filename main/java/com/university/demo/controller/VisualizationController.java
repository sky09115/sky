package com.university.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.dao.VisDao;
import com.university.demo.entity.News;
import com.university.demo.entity.Product;
import com.university.demo.entity.ProductVo;
import com.university.demo.entity.Song;
import com.university.demo.entity.request.SearchRequest;
import com.university.demo.entity.response.ChartData;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.python.TransferPython.ToPython;
import com.university.demo.service.SongService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
    private SongService songService;

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



    // 推荐算法1    ****************************
    @GetMapping("/getItemCF")
    public ServerResponse getItemCF(@RequestParam String userId) {
        List<Song> records = new ArrayList();
        String content = toPython.itemrec(userId);
        //转为json数据
        JSONArray jo = JSONObject.parseArray(content);
        for(int i=0;i<jo.size();i++){
            JSONObject  obj = jo.getJSONObject(i);
            Song item = songService.getById(obj.getInteger("iid"));
            records.add(item);
        }

        Map map = new HashMap();
        map.put("datas", records);

        return ServerResponse.ofSuccess(map);
    }
    // 推荐算法2    ****************************
    @GetMapping ("/getUserCF")
    public ServerResponse getUserCF(@RequestParam String userId) {
        List<Song> records = new ArrayList();
        String content = toPython.userrec(userId);
        //转为json数据
        JSONArray jo = JSONObject.parseArray(content);
        for(int i=0;i<jo.size();i++){
            JSONObject  obj = jo.getJSONObject(i);
            Song item = songService.getById(obj.getInteger("iid"));
            records.add(item);
        }

        Map map = new HashMap();
        map.put("datas", records);

        return ServerResponse.ofSuccess(map);
    }
    // 推荐算法3    ****************************


    // 数据分页接口
    @GetMapping ("/recordByPage")
    public ServerResponse recordByPage(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer limit,
                                       @RequestParam(defaultValue = "") String keyword) {
        QueryWrapper<Song> wrapper = new QueryWrapper<>();
        wrapper.like(!StringUtils.isEmpty(keyword), "songName", keyword);
        Page<Song> pages = new Page<>(page, limit);
        IPage<Song> iPage = songService.page(pages, wrapper);

        Map map = new HashMap();
        map.put("data", iPage.getRecords());
        map.put("pages", iPage.getPages());
        return ServerResponse.ofSuccess(map);
    }
}

