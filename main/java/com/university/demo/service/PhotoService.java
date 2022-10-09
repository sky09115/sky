package com.university.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.university.demo.entity.Photo;

import java.util.List;

public interface PhotoService extends IService<Photo> {

    // 根据itemId,type查出未删除的， 根据sort大小排序
    List<Photo> getItemPhotos(Integer itemId, String type);
}
