package com.university.demo.service.movie2;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.movie2.MovieDetailDao;
import com.university.demo.dao.movie2.MovieUserDao;
import com.university.demo.entity.movie2.MovieDetail;
import com.university.demo.entity.movie2.MovieUser;
import org.springframework.stereotype.Service;

@Service
public class MovieUserServiceImpl extends ServiceImpl<MovieUserDao, MovieUser> implements MovieUserService {

}
