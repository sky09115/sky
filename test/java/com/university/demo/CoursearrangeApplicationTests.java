package com.university.demo;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
    public void roles() {
        roleDao.selectList(null);
    }

    //@Test
    public void routes() {
        List<RouteVo> routes = new ArrayList<>();   //返回数据为RouteVo对象列表
        QueryWrapper<Route> query = new QueryWrapper<>();
        query.eq("pid", -1);  //从父菜单开始搜索
        List<Route> route = routeDao.selectList(query);
        route.forEach(r -> {
            //先给自己设置Meta，以及第一个children节点
            RouteVo vo = new RouteVo();
            BeanUtils.copyProperties(r, vo);
            Meta meta = new Meta();
            meta.setAffix(r.getAffix());
            meta.setHidden(r.getHidden());
            meta.setTitle(r.getTitle());
            meta.setRoles(r.getRoles());
            meta.setIcon(r.getIcon());
            vo.setMeta(meta);

            List<RouteVo> children = new ArrayList<>();
            RouteVo firstChild = new RouteVo();
            BeanUtils.copyProperties(vo, firstChild);
            firstChild.setPid(r.getId());
            children.add(firstChild);

            //处理其他children
            // TODO

            vo.setChildren(children);
            routes.add(vo);
        });

        System.out.println(routes);
    }

//    @Test
    public void routesService() {
        List<RouteVo> routes = routeService.getRoutes("admin");
        System.out.println(routes);
    }
}
