package com.university.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.university.demo.entity.Log;
import com.university.demo.entity.PassLog;

import java.text.ParseException;
import java.util.List;

public interface PassLogService extends IService<PassLog> {
    Integer countPass(Integer id);
}
