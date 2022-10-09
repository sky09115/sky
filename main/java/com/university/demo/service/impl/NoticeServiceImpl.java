package com.university.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.NewsDao;
import com.university.demo.dao.NoticeDao;
import com.university.demo.entity.News;
import com.university.demo.entity.Notice;
import com.university.demo.service.NewsService;
import com.university.demo.service.NoticeService;
import org.springframework.stereotype.Service;

/**
 * @author redcomet
 * @since 2021-04-17
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeDao, Notice> implements NoticeService {

}
