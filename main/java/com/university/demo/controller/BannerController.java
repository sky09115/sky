package com.university.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.entity.Banner;
import com.university.demo.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 轮播图控制器
 * @author redcomet
 * @since 2021-04-20
 */
@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerService service;

    @PostMapping("/update")
    public ServerResponse update(@RequestBody Banner record) {
        boolean b = service.updateById(record);
        if (b) {
            return ServerResponse.ofSuccess("修改成功", record);
        }
        return ServerResponse.ofError("修改失败!");
    }

    @GetMapping("/banners/{page}")
    public ServerResponse querys(@PathVariable("page") Integer page,
                                 @RequestParam(defaultValue = "10") Integer limit) {
        Page<Banner> pages = new Page<>(page, limit);
        QueryWrapper<Banner> wrapper = new QueryWrapper<Banner>().eq("deleted",false);
        IPage<Banner> iPage = service.page(pages, wrapper);
        return ServerResponse.ofSuccess(iPage);
    }
}

