package com.university.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.entity.Group;
import com.university.demo.service.GroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author redcomet
 * @since 2021-04-27
 */
@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/modify")
    public ServerResponse modify(@RequestBody Group record) {
        return groupService.updateById(record) ? ServerResponse.ofSuccess("更新成功！") : ServerResponse.ofError("更新失败！");
    }

    @GetMapping("/delete/{id}")
    public ServerResponse delete(@PathVariable("id") Integer id) {
        return groupService.removeById(id) ? ServerResponse.ofSuccess("删除成功！") : ServerResponse.ofError("删除失败！");
    }

    @GetMapping("/{id}")
    public ServerResponse query(@PathVariable("id") Integer id) {
        return ServerResponse.ofSuccess(groupService.getById(id));
    }

    @GetMapping("/groups/{page}")
    public ServerResponse querys(@PathVariable("page") Integer page,
                                       @RequestParam(defaultValue = "10") Integer limit) {
        Page<Group> pages = new Page<>(page, limit);
        QueryWrapper<Group> wrapper = new QueryWrapper<Group>().eq("deleted",false);
        IPage<Group> iPage = groupService.page(pages, wrapper);
        return ServerResponse.ofSuccess(iPage);
    }

    @GetMapping({"/search/{keyword}","/search/"})
    public ServerResponse search(@PathVariable(value = "keyword",required = false) String keyword, @RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer limit) {
        QueryWrapper<Group> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("update_time");
        wrapper.like(!StringUtils.isEmpty(keyword), "remark", keyword)
                .eq("deleted",false);
        Page<Group> pages = new Page<>(page, limit);
        IPage<Group> iPage = groupService.page(pages, wrapper);
        if (page != null) {
            return ServerResponse.ofSuccess(iPage);
        }
        return ServerResponse.ofError("查询不到数据!");
    }

    @PostMapping("/add")
    public ServerResponse add(@RequestBody Group record) {
        boolean b = groupService.save(record);
        if (b) {
            return ServerResponse.ofSuccess("添加成功", record);
        }
        return ServerResponse.ofError("添加失败!");
    }

    /* 给前端用的 */
//    @GetMapping({"/fontsearch/{username}"})
//    public ServerResponse fontsearch(@PathVariable(value = "username",required = false) String username, @RequestParam(defaultValue = "1") Integer page,
//                                     @RequestParam(defaultValue = "10") Integer limit) {
//        QueryWrapper<Group> wrapper = new QueryWrapper<>();
//        wrapper.orderByDesc("update_time");
//        wrapper.like(!StringUtils.isEmpty(username), "author", username)
//                .eq("deleted",false);
//        Page<Group> pages = new Page<>(page, limit);
//        IPage<Group> iPage = groupService.page(pages, wrapper);
//        if (page != null) {
//            return ServerResponse.ofSuccess(iPage);
//        }
//        return ServerResponse.ofError("查询不到数据!");
//    }
}

