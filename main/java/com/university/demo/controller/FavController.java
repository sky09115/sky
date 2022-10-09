package com.university.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.entity.Fav;
import com.university.demo.service.FavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 网站前端收藏控制器
 * @author redcomet
 * @since 2021-04-20
 */
@RestController
@RequestMapping("/fav")
public class FavController {

    @Autowired
    private FavService favService;

    @PostMapping("/delete")
    public ServerResponse delete(@RequestBody Fav fav) {
        QueryWrapper<Fav> wrapper = new QueryWrapper<>();
        wrapper.eq("sid",fav.getSid());
        wrapper.eq("rid",fav.getRid());
        return favService.remove(wrapper) ? ServerResponse.ofSuccess("取消收藏成功！") : ServerResponse.ofError("取消收藏失败！");
    }

    @PostMapping("/add")
    public ServerResponse add(@RequestBody Fav fav) {
        boolean b = favService.save(fav);
        if (b) {
            return ServerResponse.ofSuccess("收藏成功", fav);
        }
        return ServerResponse.ofError("收藏失败!");
    }

    @GetMapping({"/search"})
    public ServerResponse searchUser(@RequestParam(value = "keyword",required = false) String keyword) {
        List<Fav> favs =  favService.getFavs(keyword);
        return ServerResponse.ofSuccess(favs);
    }
}

