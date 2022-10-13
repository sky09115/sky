package com.university.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.CommentDao;
import com.university.demo.dao.MusicCommentDao;
import com.university.demo.entity.Comment;
import com.university.demo.entity.MusicComment;
import com.university.demo.service.CommentService;
import com.university.demo.service.MusicCommentService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MusicCommentServiceImpl extends ServiceImpl<MusicCommentDao, MusicComment> implements MusicCommentService {


}
