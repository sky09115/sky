package com.university.demo.service.movie2;

import com.baomidou.mybatisplus.extension.service.IService;
import com.university.demo.entity.movie2.ImdbRatings;
import com.university.demo.entity.movie2.MovieReviews;

import java.util.List;

public interface MovieReviewsService extends IService<MovieReviews> {
    List<Double> getRate1(String[] category);

    List<Double> getRateNum(String[] category);
    List<Double> getRate5Star(String[] category);
}
