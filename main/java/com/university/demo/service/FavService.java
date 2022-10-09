package com.university.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.university.demo.entity.Concern;
import com.university.demo.entity.Fav;

import java.util.List;

public interface FavService extends IService<Fav> {

    List<Fav> getFavs(String username);
}
