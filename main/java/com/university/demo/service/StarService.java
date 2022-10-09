package com.university.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.university.demo.entity.Star;
import com.university.demo.entity.Thumb;

import java.util.List;

public interface StarService extends IService<Star> {

    public List<Integer>  getChartData();

}
