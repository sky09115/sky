package com.university.demo;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.university.demo.dao.RoleDao;
import com.university.demo.dao.RouteDao;
import com.university.demo.service.RouteService;
import com.university.demo.util.RandomUtil;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ModelFApplication.class)
public class CoursearrangeApplicationTests {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RouteDao routeDao;

    @Autowired
    private RouteService routeService;






    // @Test
    public void testRandom() {
        for (int i = 0; i < 100; i++) {
            double d = RandomUtil.randomDouble(-500, 500);
            System.out.println(d);
        }
    }

    //@Test

}
