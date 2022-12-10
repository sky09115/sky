package com.university.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.university.demo.entity.Log;
import com.university.demo.entity.response.ChartData;

import java.text.ParseException;
import java.util.List;

public interface LogService extends IService<Log> {
    List<Integer> chartCount(String opt) throws ParseException;

    List<String> chartDay(String opt) throws ParseException;

    List<ChartData> chartData(String type);

}
