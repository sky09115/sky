package com.university.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.university.demo.entity.Msg;

public interface MsgService extends IService<Msg> {

     void sendMsg(String sid, String rid, String msgContent);
}
