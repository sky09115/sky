package com.university.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.PhotoDao;
import com.university.demo.entity.Photo;
import com.university.demo.service.PhotoService;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoDao, Photo> implements PhotoService {

    @Override
    public List<Photo> getItemPhotos(Integer itemId, String type) {
        QueryWrapper<Photo> wrapper = new QueryWrapper<>();
        wrapper.eq("iid", itemId);
        wrapper.eq("type", type);
        wrapper.eq("deleted", false);
        wrapper.orderByDesc("sort");
        List<Photo> photos = baseMapper.selectList(wrapper);
        return photos;
    }
}
