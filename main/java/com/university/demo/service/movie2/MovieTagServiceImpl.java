package com.university.demo.service.movie2;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.movie2.MovieTagDao;
import com.university.demo.entity.movie2.MovieTag;
import org.springframework.stereotype.Service;

@Service
public class MovieTagServiceImpl extends ServiceImpl<MovieTagDao, MovieTag> implements MovieTagService {

}
