package com.university.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.CommentDao;
import com.university.demo.dao.ConcernDao;
import com.university.demo.entity.Comment;
import com.university.demo.entity.Concern;
import com.university.demo.service.CommentService;
import com.university.demo.service.ConcernService;
import org.springframework.stereotype.Service;

@Service
public class ConcernServiceImpl extends ServiceImpl<ConcernDao, Concern> implements ConcernService {

}
