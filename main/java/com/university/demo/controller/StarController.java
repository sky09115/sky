package com.university.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.university.demo.anotation.SysLog;
import com.university.demo.entity.Star;
import com.university.demo.entity.Thumb;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.entity.system.SysConstant;
import com.university.demo.service.NewsService;
import com.university.demo.service.StarService;
import com.university.demo.service.ThumbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 网站前端收藏控制器
 * @author redcomet
 * @since 2021-12-29
 */
@RestController
@RequestMapping("/star")
public class StarController {

    @Autowired
    private StarService starService;

    @Autowired
    private NewsService newsService;

    @PostMapping("/delete")
    public ServerResponse delete(@RequestBody Star star) {
        newsService.doStar(star.getNid(), -1);
        QueryWrapper<Star> wrapper = new QueryWrapper<>();
        wrapper.eq("sid",star.getSid());
        wrapper.eq("nid",star.getNid());
        return starService.remove(wrapper) ? ServerResponse.ofSuccess("取消收藏成功！") : ServerResponse.ofError("取消收藏失败！");
    }

    @PostMapping("/add")
    @SysLog(value= SysConstant.FAV)
    public ServerResponse add(@RequestBody Star star) {
        newsService.doStar(star.getNid(), 1);
        boolean b = starService.save(star);
        if (b) {
            return ServerResponse.ofSuccess("收藏成功", star);
        }
        return ServerResponse.ofError("收藏失败!");
    }
}

