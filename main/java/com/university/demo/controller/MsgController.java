package com.university.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.entity.Msg;
import com.university.demo.service.MsgService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 网站前端消息控制器
 * @author redcomet
 * @since 2021-04-05
 */
@RestController
@RequestMapping("/msg")
public class MsgController {

    @Autowired
    private MsgService msgService;

    @PostMapping("/modify")
    public ServerResponse modify(@RequestBody Msg msg) {
        return msgService.updateById(msg) ? ServerResponse.ofSuccess("更新成功！") : ServerResponse.ofError("更新失败！");
    }

    @GetMapping("/delete/{id}")
    public ServerResponse delete(@PathVariable("id") Integer id) {
        return msgService.removeById(id) ? ServerResponse.ofSuccess("删除成功！") : ServerResponse.ofError("删除失败！");
    }

//    @GetMapping("/{id}")
//    public ServerResponse query(@PathVariable("id") Integer id) {
//        return ServerResponse.ofSuccess(msgService.getById(id));
//    }

    @GetMapping("/news/{page}")
    public ServerResponse querys(@PathVariable("page") Integer page,
                                       @RequestParam(defaultValue = "10") Integer limit) {
        Page<Msg> pages = new Page<>(page, limit);
        QueryWrapper<Msg> wrapper = new QueryWrapper<Msg>().eq("deleted",false);
        IPage<Msg> iPage = msgService.page(pages, wrapper);
        return ServerResponse.ofSuccess(iPage);
    }

    @GetMapping({"/search/{keyword}","/search/"})
    public ServerResponse search(@PathVariable(value = "keyword",required = false) String keyword, @RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer limit) {
        QueryWrapper<Msg> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("update_time");
        wrapper.like(!StringUtils.isEmpty(keyword), "title", keyword)
                .eq("deleted",false);
        Page<Msg> pages = new Page<>(page, limit);
        IPage<Msg> iPage = msgService.page(pages, wrapper);
        if (page != null) {
            return ServerResponse.ofSuccess(iPage);
        }
        return ServerResponse.ofError("查询不到数据!");
    }

    @PostMapping("/add")
    public ServerResponse add(@RequestBody Msg msg) {
        boolean b = msgService.save(msg);
        if (b) {
            return ServerResponse.ofSuccess("添加成功", msg);
        }
        return ServerResponse.ofError("添加失败!");
    }

    /* 获取最新一条消息 */
    @GetMapping("/{username}")
    public ServerResponse queryMsg(@PathVariable("username") String username) {
        Map map = new HashMap<>();
        QueryWrapper<Msg> wrapper = new QueryWrapper<>();
        wrapper.eq("rid",username);
        wrapper.eq("deleted",false);
        Page pages = new Page(0, 1);
        IPage<Msg> iPage = msgService.page(pages, wrapper);
        return ServerResponse.ofSuccess(iPage);
    }

    /* 读一条消息 */
    @GetMapping("/read/{id}")
    public ServerResponse read(@PathVariable("id") Integer id) {
        msgService.removeById(id);
        return ServerResponse.ofSuccess("已读");
    }

    /* 给前端用的 */

    @GetMapping({"/fontsearch/{username}"})
    public ServerResponse fontsearch(@PathVariable(value = "username",required = false) String username, @RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer limit) {
        QueryWrapper<Msg> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("update_time");
        wrapper.like(!StringUtils.isEmpty(username), "rid", username)
                .eq("deleted",false);
        Page<Msg> pages = new Page<>(page, limit);
        IPage<Msg> iPage = msgService.page(pages, wrapper);
        if (page != null) {
            return ServerResponse.ofSuccess(iPage);
        }
        return ServerResponse.ofError("查询不到数据!");
    }
}

