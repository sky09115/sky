package com.university.demo.service.movie2;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.movie2.ImdbRatingsDao;
import com.university.demo.entity.movie2.ImdbRatings;
import org.springframework.stereotype.Service;

@Service
public class ImdbRatingsServiceImpl extends ServiceImpl<ImdbRatingsDao, ImdbRatings> implements ImdbRatingsService {

}
