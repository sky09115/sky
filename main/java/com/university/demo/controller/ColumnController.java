package com.university.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.entity.Column;
import com.university.demo.service.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 栏目控制器
 * @author redcomet
 * @since 2021-10-25
 */
@RestController
@RequestMapping("/column")
public class ColumnController {

    @Autowired
    private ColumnService service;

    @PostMapping("/update")
    public ServerResponse update(@RequestBody Column record) {
        boolean b = service.updateById(record);
        if (b) {
            return ServerResponse.ofSuccess("修改成功", record);
        }
        return ServerResponse.ofError("修改失败!");
    }

    @GetMapping("/records/{page}")
    public ServerResponse querys(@PathVariable("page") Integer page,
                                 @RequestParam(defaultValue = "10") Integer limit) {
        Page<Column> pages = new Page<>(page, limit);
        QueryWrapper<Column> wrapper = new QueryWrapper<Column>().eq("deleted",false);
        IPage<Column> iPage = service.page(pages, wrapper);
        return ServerResponse.ofSuccess(iPage);
    }
}

