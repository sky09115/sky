package com.university.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.ApplyDao;
import com.university.demo.entity.Apply;
import com.university.demo.service.ApplyService;
import org.springframework.stereotype.Service;


@Service
public class ApplyServiceImpl extends ServiceImpl<ApplyDao, Apply> implements ApplyService {
}
