package com.university.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.university.demo.entity.Concern;
import com.university.demo.entity.Thumb;

import java.util.List;

public interface ThumbService extends IService<Thumb> {

    public List<Integer>  getChartData();

}
