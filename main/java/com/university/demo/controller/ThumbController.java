package com.university.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.entity.Thumb;
import com.university.demo.service.NewsService;
import com.university.demo.service.ThumbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 网站前端点赞控制器
 * @author redcomet
 * @since 2021-04-06
 */
@RestController
@RequestMapping("/thumb")
public class ThumbController {

    @Autowired
    private ThumbService thumbService;

    @Autowired
    private NewsService newsService;

    @PostMapping("/delete")
    public ServerResponse delete(@RequestBody Thumb thumb) {
        newsService.doThumb(thumb.getNid(), -1);
        QueryWrapper<Thumb> wrapper = new QueryWrapper<>();
        wrapper.eq("sid",thumb.getSid());
        wrapper.eq("nid",thumb.getNid());
        return thumbService.remove(wrapper) ? ServerResponse.ofSuccess("取消点赞成功！") : ServerResponse.ofError("取消点赞失败！");
    }

    @PostMapping("/add")
    public ServerResponse add(@RequestBody Thumb thumb) {
        newsService.doThumb(thumb.getNid(), 1);
        boolean b = thumbService.save(thumb);
        if (b) {
            return ServerResponse.ofSuccess("点赞成功", thumb);
        }
        return ServerResponse.ofError("点赞失败!");
    }
}

