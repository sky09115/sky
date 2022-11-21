package com.university.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.WeatherDao;
import com.university.demo.entity.Weather;
import com.university.demo.service.WeatherService;
import org.springframework.stereotype.Service;


@Service
public class WeatherServiceImpl extends ServiceImpl<WeatherDao, Weather> implements WeatherService {
}