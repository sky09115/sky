package com.university.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.LogDao;
import com.university.demo.dao.VisDao;
import com.university.demo.entity.Log;
import com.university.demo.entity.response.ChartData;
import com.university.demo.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogServiceImpl extends ServiceImpl<LogDao, Log> implements LogService {

    @Autowired
    VisDao visDao;



    @Override
    public List<Integer> chartCount(String  opt) throws ParseException {
        List<Integer> values = new ArrayList<>();
        List<String> days = baseMapper.chart(opt);
        for(String _day : days){
            QueryWrapper<Log> query = new QueryWrapper<>();
            query.like("create_time",_day);
//
            values.add(baseMapper.selectCount(query));
        }
        return values;
    }

//    @Override
//    public List<Integer> reviewCount() throws ParseException {
//        List<Integer> values = new ArrayList<>();
//        List<String> days = visDao.review_day();
//        for(String _day : days){
//            QueryWrapper<MovieReviews> query = new QueryWrapper<>();
//            query.like("user_movie_rating_time",_day);
//            values.add(movieReviewsDao.selectCount(query));
//        }
//        return values;
//    }

    @Override
    public List<String> chartDay(String opt) throws ParseException {
        return  baseMapper.chart(opt);
    }

    @Override
    public List<ChartData> chartData(String type) {
        if("GROUP_MAN".equals(type))
            return baseMapper.groupManCount();
        else if("ROLE_MAN".equals(type))
            return baseMapper.roleManCount();
        else
            return new ArrayList<>();
    }
}
