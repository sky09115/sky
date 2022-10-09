package com.university.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.entity.Notice;
import com.university.demo.entity.request.SearchRequest;
import com.university.demo.service.NoticeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author redcomet
 * @since 2021-04-19
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @PostMapping("/modify")
    public ServerResponse modify(@RequestBody Notice notice) {
        return noticeService.updateById(notice) ? ServerResponse.ofSuccess("更新成功！") : ServerResponse.ofError("更新失败！");
    }

    @GetMapping("/delete/{id}")
    public ServerResponse delete(@PathVariable("id") Integer id) {
        return noticeService.removeById(id) ? ServerResponse.ofSuccess("删除成功！") : ServerResponse.ofError("删除失败！");
    }

    @GetMapping("/{id}")
    public ServerResponse query(@PathVariable("id") Integer id) {
        return ServerResponse.ofSuccess(noticeService.getById(id));
    }

    @GetMapping("/notices/{page}")
    public ServerResponse querys(@PathVariable("page") Integer page,
                                       @RequestParam(defaultValue = "10") Integer limit) {
        Page<Notice> pages = new Page<>(page, limit);
        QueryWrapper<Notice> wrapper = new QueryWrapper<Notice>().eq("deleted",false);
        IPage<Notice> iPage = noticeService.page(pages, wrapper);
        return ServerResponse.ofSuccess(iPage);
    }

    @PostMapping("/search2")
    public ServerResponse search2(@RequestBody SearchRequest params,
                                  @RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer limit) {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        wrapper.like(!StringUtils.isEmpty(params.getKeyword()), "title", params.getKeyword())
                .eq(!StringUtils.isEmpty(params.getType()), "type", params.getType())
                .eq("deleted",false);
        Page<Notice> pages = new Page<>(page, limit);
        IPage<Notice> iPage = noticeService.page(pages, wrapper);
        if (page != null) {
            return ServerResponse.ofSuccess(iPage);
        }
        return ServerResponse.ofError("查询不到数据!");
    }

    @GetMapping({"/search/{keyword}","/search/"})
    public ServerResponse search(@PathVariable(value = "keyword",required = false) String keyword, @RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer limit) {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("update_time");
        wrapper.like(!StringUtils.isEmpty(keyword), "title", keyword)
                .eq("deleted",false);
        Page<Notice> pages = new Page<>(page, limit);
        IPage<Notice> iPage = noticeService.page(pages, wrapper);
        if (page != null) {
            return ServerResponse.ofSuccess(iPage);
        }
        return ServerResponse.ofError("查询不到数据!");
    }

    @PostMapping("/add")
    public ServerResponse add(@RequestBody Notice notice) {
        boolean b = noticeService.save(notice);
        if (b) {
            return ServerResponse.ofSuccess("添加成功", notice);
        }
        return ServerResponse.ofError("添加失败!");
    }

    /* 给前端用的 */

    @GetMapping({"/fontsearch/{username}"})
    public ServerResponse fontsearch(@PathVariable(value = "username",required = false) String username, @RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer limit) {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("update_time");
        wrapper.eq("deleted",false);
        Page<Notice> pages = new Page<>(page, limit);
        IPage<Notice> iPage = noticeService.page(pages, wrapper);
        if (page != null) {
            return ServerResponse.ofSuccess(iPage);
        }
        return ServerResponse.ofError("查询不到数据!");
    }

}

