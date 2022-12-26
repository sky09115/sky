package com.university.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.university.demo.dao.UserDao;
import com.university.demo.dao.order.OrderDao;
import com.university.demo.entity.game.Game;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.entity.system.SysConstant;
import com.university.demo.python.TransferPython.ToPython;

import com.university.demo.service.LogService;
import com.university.demo.service.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/spark")
public class SparkController {


    @Autowired
    LogService logService;
    @Autowired
    UserDao userDao;
    @Autowired
    OrderDao orderDao;
//    @Autowired
//    SparkUtils sparkUtils;

    @Autowired
    GameService gameService;

    @Autowired
    ToPython toPython;

    /**
     *  spark 大屏 page1
     */

    /**
     * 大屏数据-统计值
     * @return
     */
    @RequestMapping(value = "/dash1", method = RequestMethod.GET)
    public ServerResponse Dash1() {
        Map map = new HashMap();
//        System.out.println(orderDao.selectCount(null));
//        System.out.println( userDao.selectCount(null)*1.0);

        map.put("deposit_rank", orderDao.getPay());
        map.put("province_users", userDao.getUserProvince());
//        map.put("users", userDao.getUsersCount());
//        map.put("orders", orderDao.selectCount(null));
//        map.put("shops", sparkUtils.count("bcat_list"));
//        map.put("cates", sparkUtils.count("gcat_list"));
        //map.put("goods", sparkUtils.count("gcat_list"));
//        map.put("shops", albumService.count(null));
//        map.put("cates", artistService.count(null));
//        map.put("goods", songService.count(null));

//        map.put("test_user", sparkUtils.count("tb_user"));
//        map.put("orderSum", orderDao.getOrderSum());


//        map.put("books", orderDao.getBook());
        // 通过业务表汇总
//        map.put("books2", sparkUtils.count("goods_list"));
//        map.put("users", sparkUtils.count("tb_user"));
//        map.put("deposits", sparkUtils.count("tb_order"));
//        map.put("favs", sparkUtils.countLog("tb_collect"));
//        map.put("plays", productDao.getOrderSum());
//        map.put("authors", productDao.getAuthorCount());

        // 通过日志汇总
//        map.put("logins", sparkUtils.countLog("login"));
//        map.put("app_logins", sparkUtils.countLog("app_login"));
//        map.put("favs", sparkUtils.countLog("tb_star"));
//        map.put("downloads", sparkUtils.countLog("rate"));
//        map.put("plays", sparkUtils.countLog("tb_thumb"));
//        map.put("app_plays", sparkUtils.countLog("app_reserve"));
//        map.put("idconfirm", sparkUtils.countLog("idconfirm"));

        return ServerResponse.ofSuccess(map);
    }

    @RequestMapping(value = "/logins", method = RequestMethod.GET)
    public ServerResponse Logins() throws ParseException {
        Map map = new HashMap();
//        map.put("logins", weatherDao.getHistoryHumidity(city));
        map.put("xData",logService.chartDay(SysConstant.LOGIN));
        map.put("logins",logService.chartCount(SysConstant.LOGIN));
//        map.put("applogins",logService.chartCount(SysConstant.APP_LOGIN));

        return ServerResponse.ofSuccess(map);
    }

    @RequestMapping(value = "/dash20", method = RequestMethod.GET)
    public ServerResponse Dash20(@RequestParam(defaultValue = "北京市") String city) {
        Map map = new HashMap();
//        map.put("hotrank", weatherDao.getHistoryPrecipitation(city));
        return ServerResponse.ofSuccess(map);
    }

    @RequestMapping(value = "/dash21", method = RequestMethod.GET)
    public ServerResponse dash21(@RequestParam(defaultValue = "北京市") String city) {
        Map map = new HashMap();
//        map.put("rank", weatherDao.getHistoryWeather(city));
        return ServerResponse.ofSuccess(map);
    }

    @RequestMapping(value = "/dash22", method = RequestMethod.GET)
    public ServerResponse dash22(@RequestParam(defaultValue = "北京市") String city) {
        Map map = new HashMap();
//        String t = weatherDao.getCurrentDay();
//        map.put("provinceRank", weatherDao.getProvinceWeather());
//        map.put("cityRank", weatherDao.getCityWeather());
//        map.put("districtRank", weatherDao.getCountyWeather());
        return ServerResponse.ofSuccess(map);
    }

    @RequestMapping(value = "/dash2", method = RequestMethod.GET)
    public ServerResponse Dash2() {
        Map map = new HashMap();
        // 通过日志汇总
//        map.put("deposit_rank", orderDao.getDepositRank());

//        map.put("financeStages", jobDao.getProvinceFinanceStages(10));
//        map.put("provinceJobs", jobDao.getProvinceJobs(10));
//        map.put("workYears", jobDao.getWorkYear(10));
//        map.put("jobNatures", jobDao.getJobNature(10));
//        map.put("workYearGroup", jobDao.getWorkYearGroup(10));
//        map.put("jobNatureGroup", jobDao.getJobNatureGroup(10));
//        map.put("industryFields", getIndustryFields());
//        map.put("industryFields2", getIndustryFields2());

        return ServerResponse.ofSuccess(map);
    }

    @RequestMapping(value = "/dash3", method = RequestMethod.GET)
    public ServerResponse Dash3(@RequestParam(defaultValue = "北京市") String city) {
        Map map = new HashMap();
        // 通过日志汇总
//        map.put("orders", orderDao.getUserOrderRank());
//        map.put("order2022", getOrderSumByMonth("2022"));
//        map.put("order2021", weatherDao.getHistoryWindPowder(city));
//        map.put("order2022", weatherDao.getHistoryPressure(city));
//        map.put("orderCount2021", weatherDao.getHistoryWindPowder(city));
//        map.put("orderCount2022", weatherDao.getHistoryPressure(city));
//        map.put("guangzhou", weatherDao.getHistoryWeather("广州市"));
//        map.put("shenzhen", weatherDao.getHistoryWeather("深圳市"));
//        map.put("shanghai", weatherDao.getHistoryWeather("上海市"));

//        map.put("cars2022", getCarsSum("2022"));
//        map.put("tickets2022", getTicketsSumByMonth("2022"));
//        map.put("insures2022", getInsuresSum("2022"));
        return ServerResponse.ofSuccess(map);
    }

    @RequestMapping(value = "/dash4", method = RequestMethod.GET)
    public ServerResponse Dash4() {
        Map map = new HashMap();
//        map.put("agents", sparkUtils.count("tb_agent"));
//        map.put("reserves", sparkUtils.count("tb_reserve"));
//        map.put("user", sparkUtils.count("tb_user"));
//        map.put("orders", sparkUtils.count("tb_order"));
//        map.put("jobs", sparkUtils.count("lagou_data"));
////        map.put("companies", jobDao.getDistinctCompanyNames());
//        map.put("resumes", sparkUtils.count("tb_resume"));

        return ServerResponse.ofSuccess(map);
    }

//    @RequestMapping(value = "/mapData", method = RequestMethod.GET)
//    public ServerResponse mapData() {
//        Map map = new HashMap();
//        map.put("world", getWorldPositions());
//
//        return ServerResponse.ofSuccess(map);
//    }

    @RequestMapping(value = "/industryFields", method = RequestMethod.GET)
    public ServerResponse IndustryFields(@RequestParam(defaultValue = "北京市") String city) throws ParseException {
        Map map = new HashMap();
//        map.put("industryFields", weatherDao.getHistoryWeather(city));
        return ServerResponse.ofSuccess(map);
    }

//    @RequestMapping(value = "/getPositionNames", method = RequestMethod.GET)
//    public ServerResponse getPositionNames() {
//        Map map = new HashMap();
//        // 通过日志汇总
//        map.put("positionNames", getPositionNameCount());
//        map.put("users", userDao.selectCount(null));
//
//        return ServerResponse.ofSuccess(map);
//    }

    @RequestMapping(value = "/playcnt", method = RequestMethod.GET)
    public ServerResponse Playcnt() {
        Map map = new HashMap();
        // 通过日志汇总
//        map.put("playcnt_rank", newsDao.getRank());
        return ServerResponse.ofSuccess(map);
    }

    @RequestMapping(value = "/provinces", method = RequestMethod.GET)
    public ServerResponse Provinces() {
        Map map = new HashMap();
        // 通过日志汇总
//        map.put("province_users", userDao.getProvinceUsers());

        return ServerResponse.ofSuccess(map);
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public ServerResponse orders() throws ParseException {
        Map map = new HashMap();

//        map.put("xData",orderService.chartDay());
//        map.put("orders",orderService.chartCount());

        return ServerResponse.ofSuccess(map);
    }



    @RequestMapping(value = "/plays", method = RequestMethod.GET)
    public ServerResponse Plays() throws ParseException {
        Map map = new HashMap();
//        map.put("xData",logService.chartDay(SysConstant.RESERVE));
//        map.put("plays",logService.chartCount(SysConstant.RESERVE));

        return ServerResponse.ofSuccess(map);
    }

    @RequestMapping(value = "/top5songs", method = RequestMethod.GET)
    public ServerResponse Top5songs() throws ParseException {
        Map map = new HashMap();
        List<String> songs = new ArrayList<>();
//        List<ThreeData> datas = newsDao.getTopRate();
//        for (ThreeData data : datas) {
//            News news = newsDao.selectById(data.getName1());
//            songs.add(news.getTitle());
//        }

        map.put("topsongs",songs);
        return ServerResponse.ofSuccess(map);
    }



    public List<Double> getOrderSumByMonth(String year){
        List<Double> datas = new ArrayList<>();
        String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};
//        for (int i=0;i<months.length;i++){
//            Double d = orderDao.getAmountByMonth(year, months[i]);
//            if(d==null)
//                datas.add(0.0);
//            else
//                datas.add(d);
//        }
        return datas;
    }

    public List<Double> getOrderCountByMonth(String year){
        List<Double> datas = new ArrayList<>();
        String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};
        for (int i=0;i<months.length;i++){
//            Double d = orderDao.getCountByMonth(year, months[i]);
//            if(d==null)
//                datas.add(0.0);
//            else
//                datas.add(d);
        }
        return datas;
    }

    /***********  python scripts begin   ************/
    @RequestMapping(value = "/usercf", method = RequestMethod.GET)
    public ServerResponse testPython(@RequestParam(defaultValue = "1") String uid) throws ParseException {
        Map map = new HashMap();
        List<Game> dataList  = new ArrayList<>();
        //调用python脚本
        String content = toPython.userrec(uid);
        //转为json数据
        JSONArray jo = JSONObject.parseArray(content);
        for(int i=0; i<jo.size(); i++){
            JSONObject o = jo.getJSONObject(i);
            System.out.println(o.get("iid"));
            Game game = gameService.getById((Serializable) o.get("iid"));
            dataList.add(game);
        }

        map.put("rec",dataList);
        return ServerResponse.ofSuccess(map);
    }

    @RequestMapping(value = "/itemcf", method = RequestMethod.GET)
    public ServerResponse testPython2(@RequestParam(defaultValue = "1") String uid) throws ParseException {
        Map map = new HashMap();
        List<Game> dataList  = new ArrayList<>();
        //调用python脚本
        String content = toPython.itemrec(uid);
        //转为json数据
        JSONArray jo = JSONObject.parseArray(content);
        for(int i=0; i<jo.size(); i++){
            JSONObject o = jo.getJSONObject(i);
            System.out.println(o.get("iid"));
            Game game = gameService.getById((Serializable) o.get("iid"));
            dataList.add(game);
        }

        map.put("rec",dataList);
        return ServerResponse.ofSuccess(map);
    }
    /***********  python scripts end   ************/

    public List<Double> getTicketsSumByMonth(String year){
        List<Double> datas = new ArrayList<>();
        String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};
        for (int i=0;i<months.length;i++){
//            Double d = orderDao.getTicketsByMonth(year, months[i]);
//            if(d==null)
//                datas.add(0.0);
//            else
//                datas.add(d);
        }
        return datas;
    }

    public List<Double> getInsuresSum(String year){
        List<Double> datas = new ArrayList<>();
        String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};
        for (int i=0;i<months.length;i++){
//            Double d = orderDao.getInsuresByMonth(year, months[i]);
//            if(d==null)
//                datas.add(0.0);
//            else
//                datas.add(d);
        }
        return datas;
    }

    public List<Double> getCarsSum(String year){
        List<Double> datas = new ArrayList<>();
        String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};
        for (int i=0;i<months.length;i++){
//            Double d = orderDao.getCarsByMonth(year, months[i]);
//            if(d==null)
//                datas.add(0.0);
//            else
//                datas.add(d);
        }
        return datas;
    }
}
