package com.university.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.controller.base.BaseController;
import com.university.demo.entity.*;
import com.university.demo.entity.request.SearchRequest;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 */
@RestController
@RequestMapping("/song")
public class SongController extends BaseController<Song> {

    @PostMapping("/search")
    public ServerResponse search2(@RequestBody SearchRequest params,
                                  @RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer limit) {
        QueryWrapper<Song> wrapper = new QueryWrapper<>();
        wrapper.like(!StringUtils.isEmpty(params.getKeyword()), "songName", params.getKeyword());
        Page<Song> pages = new Page<>(page, limit);
        IPage<Song> iPage = baseSerivce.page(pages, wrapper);
        if (page != null) {
            return ServerResponse.ofSuccess(iPage);
        }
        return ServerResponse.ofError("查询不到数据!");
    }
}

