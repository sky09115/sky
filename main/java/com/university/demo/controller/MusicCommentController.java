package com.university.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.entity.Comment;
import com.university.demo.entity.MusicComment;
import com.university.demo.entity.request.SearchRequest;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.python.TransferPython.ToPython;
import com.university.demo.service.CommentService;
import com.university.demo.service.MusicCommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * @since 2022年10月13日
 */
@RestController
@RequestMapping("/musiccomment")
public class MusicCommentController {

    @Autowired
    private MusicCommentService commentService;

    @Autowired
    ToPython toPython;

    @PostMapping("/modify")
    public ServerResponse modify(@RequestBody MusicComment record) {
        return commentService.updateById(record) ? ServerResponse.ofSuccess("更新成功！") : ServerResponse.ofError("更新失败！");
    }

    @GetMapping("/delete/{id}")
    public ServerResponse delete(@PathVariable("id") Integer id) {
        return commentService.removeById(id) ? ServerResponse.ofSuccess("删除成功！") : ServerResponse.ofError("删除失败！");
    }

    @GetMapping("/{id}")
    public ServerResponse query(@PathVariable("id") Integer id) {
        return ServerResponse.ofSuccess(commentService.getById(id));
    }

    @GetMapping("/records/{page}")
    public ServerResponse querys(@PathVariable("page") Integer page,
                                       @RequestParam(defaultValue = "10") Integer limit) {
        Page<MusicComment> pages = new Page<>(page, limit);
        QueryWrapper<MusicComment> wrapper = new QueryWrapper<MusicComment>().eq("deleted",false);
        IPage<MusicComment> iPage = commentService.page(pages, wrapper);
        return ServerResponse.ofSuccess(iPage);
    }

    @PostMapping({"/search"})
    public ServerResponse search(@RequestBody SearchRequest query, @RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer limit) {
        QueryWrapper<MusicComment> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        wrapper.like(!StringUtils.isEmpty(query.getKeyword()), "content", query.getKeyword())
                .eq("deleted",false);
        Page<MusicComment> pages = new Page<>(page, limit);
        IPage<MusicComment> iPage = commentService.page(pages, wrapper);
        if (page != null) {
            return ServerResponse.ofSuccess(iPage);
        }
        return ServerResponse.ofError("查询不到数据!");
    }

    @PostMapping("/add")
    public ServerResponse add(@RequestBody MusicComment record) {
//        news.setStar(0);
//        news.setThumb(0);
        String c = record.getContent();
        //情感分析 Python 脚本调用 LSTM
        String content = toPython.lstm(c);
        JSONArray jo = JSONObject.parseArray(content);
        JSONObject  obj = jo.getJSONObject(0);
        String label = obj.getString("sentiment_key");
        Double positive_probs = obj.getDouble("positive_probs");
        Double probs = positive_probs;
        if(label.equals("negative")){
            probs = 1 - probs;
        }

        record.setLabel(label);
        record.setScore(probs);

        boolean b = commentService.save(record);
        if (b) {
            return ServerResponse.ofSuccess("添加成功", record);
        }
        return ServerResponse.ofError("添加失败!");
    }

    /* 给前端用的 */

    @GetMapping({"/fontsearch/{username}"})
    public ServerResponse fontsearch(@PathVariable(value = "username",required = false) String username, @RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer limit) {
        QueryWrapper<MusicComment> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("update_time");
        wrapper.like(!StringUtils.isEmpty(username), "author", username)
                .eq("deleted",false);
        Page<MusicComment> pages = new Page<>(page, limit);
        IPage<MusicComment> iPage = commentService.page(pages, wrapper);
        if (page != null) {
            return ServerResponse.ofSuccess(iPage);
        }
        return ServerResponse.ofError("查询不到数据!");
    }

    @GetMapping({"/getCommentByGoodsId"})
    public ServerResponse getCommentByGoodsId(@RequestParam Integer goodsId,
                                              @RequestParam(defaultValue = "1") Integer page,
                                              @RequestParam(defaultValue = "20") Integer limit) {
        QueryWrapper<MusicComment> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("update_time");
        wrapper.eq( "goods_id", goodsId)
                .eq("deleted",false);
        Page<MusicComment> pages = new Page<>(page, limit);
        IPage<MusicComment> iPage = commentService.page(pages, wrapper);
        if (page != null) {
            return ServerResponse.ofSuccess(iPage);
        }
        return ServerResponse.ofError("查询不到数据!");
    }

    // LSTM 情感分析测试
    @GetMapping({"/testLstm"})
    public ServerResponse testLstm() {
        String content = toPython.lstm("两个小孩子");
        JSONArray jo = JSONObject.parseArray(content);
        JSONObject  obj = jo.getJSONObject(0);
        String label = obj.getString("sentiment_key");
        Double positive_probs = obj.getDouble("positive_probs");
        Double probs = positive_probs;
        if(label.equals("negative")){
            probs = 1 - probs;
        }

        System.out.println(label);
        System.out.println(positive_probs);

        return ServerResponse.ofSuccess(label);
    }
}

