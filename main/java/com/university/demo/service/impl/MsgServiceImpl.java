package com.university.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.MsgDao;
import com.university.demo.entity.Msg;
import com.university.demo.service.MsgService;
import org.springframework.stereotype.Service;

@Service
public class MsgServiceImpl extends ServiceImpl<MsgDao, Msg> implements MsgService {

    @Override
    public void sendMsg(String sid, String rid, String msgContent) {
        Msg msg = new Msg();
        msg.setSid(sid);
        msg.setRid(rid);
        msg.setContent(msgContent);

        baseMapper.insert(msg);
    }
}
