package com.university.demo.service.movie2;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.movie2.MovieReviewsDao;
import com.university.demo.entity.movie2.MovieReviews;
import org.springframework.stereotype.Service;

@Service
public class MovieReviewsServiceImpl extends ServiceImpl<MovieReviewsDao, MovieReviews> implements MovieReviewsService {

}
