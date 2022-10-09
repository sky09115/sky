package com.university.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.ConcernDao;
import com.university.demo.dao.ThumbDao;
import com.university.demo.entity.Concern;
import com.university.demo.entity.Log;
import com.university.demo.entity.Thumb;
import com.university.demo.service.ConcernService;
import com.university.demo.service.ThumbService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ThumbServiceImpl extends ServiceImpl<ThumbDao, Thumb> implements ThumbService {

    @Override
    public List<Integer> getChartData() {
        List<Integer> values = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String currentYear = sdf.format(date);

        String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12"};
        for(String _m : months){
            QueryWrapper<Thumb> query = new QueryWrapper<>();
            query.like("create_time",currentYear + "-" + _m);
            values.add(baseMapper.selectCount(query));
        }
        return values;
    }
}
