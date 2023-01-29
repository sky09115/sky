package com.university.demo.service.traffic;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.traffic.RoadDao;
import com.university.demo.entity.traffic.Road;
import org.springframework.stereotype.Service;

@Service
public class RoadServiceImpl extends ServiceImpl<RoadDao, Road> implements RoadService {

}
