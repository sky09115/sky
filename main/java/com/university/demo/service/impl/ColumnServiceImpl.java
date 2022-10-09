package com.university.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.ColumnDao;
import com.university.demo.entity.Column;
import com.university.demo.service.ColumnService;
import org.springframework.stereotype.Service;

@Service
public class ColumnServiceImpl extends ServiceImpl<ColumnDao, Column> implements ColumnService {

}
