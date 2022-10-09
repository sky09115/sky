package com.university.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.BannerDao;
import com.university.demo.dao.FavDao;
import com.university.demo.entity.Banner;
import com.university.demo.entity.Fav;
import com.university.demo.service.BannerService;
import com.university.demo.service.FavService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerServiceImpl extends ServiceImpl<BannerDao, Banner> implements BannerService {

}
