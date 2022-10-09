package com.university.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.AdminDao;
import com.university.demo.dao.NewsDao;
import com.university.demo.entity.Admin;
import com.university.demo.entity.News;
import com.university.demo.service.AdminService;
import com.university.demo.service.NewsService;
import org.springframework.stereotype.Service;

/**
 * @author redcomet
 * @since 2021-04-02
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsDao, News> implements NewsService {

    @Override
    public void doStar(Integer id, Integer var) {
        News news = baseMapper.selectById(id);
        Integer stars = 0;
        if(news!=null)
            stars = news.getStar();
        news.setStar(stars+var);
        baseMapper.updateById(news);
    }

    @Override
    public void doThumb(Integer id, Integer var) {
        News news = baseMapper.selectById(id);
        Integer thumbs = 0;
        if(news!=null)
           thumbs = news.getThumb();
        news.setStar(thumbs+var);
        baseMapper.updateById(news);
    }
}
