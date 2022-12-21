package com.university.demo.controller.game;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.anotation.SysLog;
import com.university.demo.controller.base.BaseController;
import com.university.demo.controller.base.MyWrapper;
import com.university.demo.entity.game.Game;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.entity.system.SysConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenqi
 * @since  2022-12-21
 * @说明： 1. list 支持一个搜索参数search，search_fields 支持多字段模糊查询
 *        2. list search_filter为拓展的get请求参数,配置了前台可以不传,不影响查询结果
 */
@RestController
@RequestMapping("/game")
public class GameController extends BaseController<Game> {

    protected String[] search_fields = new String[]{"name" };
    protected String[] search_filter = new String[]{"type", "gametypename"};

    @Override
    @GetMapping("/")
    public ServerResponse list(HttpServletRequest request,
                               @RequestParam(defaultValue = "") String search,
                               @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "15") Integer limit
                               ) {
        Page<Game> pages = new Page<>(page, limit);
        MyWrapper<Game> wrapperFactory = new MyWrapper<>();
        QueryWrapper<Game> wrapper = wrapperFactory.init(request, search, search_fields, search_filter);
        IPage<Game> iPage = baseSerivce.page(pages, wrapper);
        return ServerResponse.ofSuccess(iPage);
    }
}

