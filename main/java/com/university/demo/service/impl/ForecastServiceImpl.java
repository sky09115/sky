package com.university.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.service.ForecastService;
import org.springframework.stereotype.Service;


@Service
public class ForecastServiceImpl extends ServiceImpl<ForecastDao, Forecast> implements ForecastService {
}
