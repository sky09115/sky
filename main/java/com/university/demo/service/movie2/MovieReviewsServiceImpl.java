package com.university.demo.service.movie2;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.movie2.MovieReviewsDao;
import com.university.demo.entity.movie2.MovieReviews;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieReviewsServiceImpl extends ServiceImpl<MovieReviewsDao, MovieReviews> implements MovieReviewsService {
    public List<Double> getRate1(String[] category){
        List<Double> rates = new ArrayList<>();
        for (String c : category){
            Double rate = baseMapper.getRate(c);
            rates.add(rate);
        }
        return rates;
    }

    public List<Double> getRateNum(String[] category){
        List<Double> rates = new ArrayList<>();
        for (String c : category){
            Double rate = baseMapper.getRateNum(c);
            rates.add(rate);
        }
        return rates;
    }

    public List<Double> getRate5Star(String[] category){
        List<Double> rates = new ArrayList<>();
        for (String c : category){
            Double rate = baseMapper.getRate5Star(c);
            rates.add(rate);
        }
        return rates;
    }

}
