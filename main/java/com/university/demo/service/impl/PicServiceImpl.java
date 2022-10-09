package com.university.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.LogDao;
import com.university.demo.dao.PicDao;
import com.university.demo.entity.Log;
import com.university.demo.entity.Pic;
import com.university.demo.service.LogService;
import com.university.demo.service.PicService;
import org.springframework.stereotype.Service;

@Service
public class PicServiceImpl extends ServiceImpl<PicDao, Pic> implements PicService {
}
