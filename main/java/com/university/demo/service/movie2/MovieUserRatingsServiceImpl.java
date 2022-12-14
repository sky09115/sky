package com.university.demo.service.movie2;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.movie2.MovieUserRatingsDao;
import com.university.demo.entity.movie2.MovieUserRatings;
import org.springframework.stereotype.Service;

@Service
public class MovieUserRatingsServiceImpl extends ServiceImpl<MovieUserRatingsDao, MovieUserRatings> implements MovieUserRatingsService {

}