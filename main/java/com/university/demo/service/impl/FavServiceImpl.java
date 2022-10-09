package com.university.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.ConcernDao;
import com.university.demo.dao.FavDao;
import com.university.demo.entity.Concern;
import com.university.demo.entity.Fav;
import com.university.demo.service.ConcernService;
import com.university.demo.service.FavService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavServiceImpl extends ServiceImpl<FavDao, Fav> implements FavService {

    @Override
    public List<Fav> getFavs(String username) {
        return baseMapper.getFavs(username);
    }
}
