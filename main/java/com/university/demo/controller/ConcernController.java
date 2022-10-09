package com.university.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.entity.Concern;
import com.university.demo.service.ConcernService;
import com.university.demo.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 网站前端关注控制器
 * @author redcomet
 * @since 2021-04-06
 */
@RestController
@RequestMapping("/concern")
public class ConcernController {

    @Autowired
    private ConcernService concernService;

    @Autowired
    private NewsService newsService;

    @PostMapping("/delete")
    public ServerResponse delete(@RequestBody Concern concern) {
        QueryWrapper<Concern> wrapper = new QueryWrapper<>();
        wrapper.eq("sid",concern.getSid());
        wrapper.eq("rid",concern.getRid());
        return concernService.remove(wrapper) ? ServerResponse.ofSuccess("取消关注成功！") : ServerResponse.ofError("取消关注失败！");
    }

    @PostMapping("/add")
    public ServerResponse add(@RequestBody Concern concern) {
        boolean b = concernService.save(concern);
        if (b) {
            return ServerResponse.ofSuccess("关注成功", concern);
        }
        return ServerResponse.ofError("关注失败!");
    }
}

