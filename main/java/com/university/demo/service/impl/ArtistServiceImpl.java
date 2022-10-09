package com.university.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.ArtistDao;
import com.university.demo.entity.Artist;
import com.university.demo.service.ArtistService;
import org.springframework.stereotype.Service;

@Service
public class ArtistServiceImpl extends ServiceImpl<ArtistDao, Artist> implements ArtistService {

}
