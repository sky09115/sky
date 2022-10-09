package com.university.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.entity.SysConfig;
import com.university.demo.entity.request.SearchRequest;
import com.university.demo.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统参数
 * @author redcomet
 * @since 2021-11-02
 */
@RestController
@RequestMapping("/sysconfig")
public class SysConfigController {

    @Autowired
    private SysConfigService service;

    @PostMapping("/modify")
    public ServerResponse modify(@RequestBody SysConfig record) {
        return service.updateById(record) ? ServerResponse.ofSuccess("更新成功！") : ServerResponse.ofError("更新失败！");
    }

    @GetMapping("/delete/{id}")
    public ServerResponse delete(@PathVariable("id") Integer id) {
        return service.removeById(id) ? ServerResponse.ofSuccess("删除成功！") : ServerResponse.ofError("删除失败！");
    }

    @GetMapping("/{id}")
    public ServerResponse query(@PathVariable("id") Integer id) {
        return ServerResponse.ofSuccess(service.getById(id));
    }

    @GetMapping("/records/{page}")
    public ServerResponse querys(@PathVariable("page") Integer page,
                                       @RequestParam(defaultValue = "10") Integer limit) {
        Page<SysConfig> pages = new Page<>(page, limit);
        QueryWrapper<SysConfig> wrapper = new QueryWrapper<SysConfig>();
        IPage<SysConfig> iPage = service.page(pages, wrapper);
        return ServerResponse.ofSuccess(iPage);
    }

    @PostMapping({"/search"})
    public ServerResponse search(@RequestBody SearchRequest query, @RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer limit) {
        QueryWrapper<SysConfig> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");;
        Page<SysConfig> pages = new Page<>(page, limit);
        IPage<SysConfig> iPage = service.page(pages, wrapper);
        if (page != null) {
            return ServerResponse.ofSuccess(iPage);
        }
        return ServerResponse.ofError("查询不到数据!");
    }

    @PostMapping("/add")
    public ServerResponse add(@RequestBody SysConfig record) {
        boolean b = service.save(record);
        if (b) {
            return ServerResponse.ofSuccess("添加成功", record);
        }
        return ServerResponse.ofError("添加失败!");
    }

}

