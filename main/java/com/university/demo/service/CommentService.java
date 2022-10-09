package com.university.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.university.demo.entity.Comment;
import com.university.demo.entity.Msg;

import java.util.List;

public interface CommentService extends IService<Comment> {
    public List<Integer> getChartData();
}
