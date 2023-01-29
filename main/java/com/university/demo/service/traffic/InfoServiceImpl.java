package com.university.demo.service.traffic;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.traffic.InfoDao;
import com.university.demo.entity.traffic.Info;
import org.springframework.stereotype.Service;

@Service
public class InfoServiceImpl extends ServiceImpl<InfoDao, Info> implements InfoService {

}
