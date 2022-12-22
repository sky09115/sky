package com.university.demo.controller.game;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.controller.base.BaseController;
import com.university.demo.controller.base.MyWrapper;
import com.university.demo.dao.game.GameServerDao;
import com.university.demo.entity.game.GameServer;
import com.university.demo.entity.game.GameServerVo;
import com.university.demo.entity.system.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenqi
 * @since  2022-12-21
 * @说明： 1. list 支持一个搜索参数search，search_fields 支持多字段模糊查询
 *        2. list search_filter为拓展的get请求参数,配置了前台可以不传,不影响查询结果
 */
@RestController
@RequestMapping("/gameserver")
public class GameServerController extends BaseController<GameServer> {
    @Autowired
    protected GameServerDao dao;
    protected String[] search_fields = new String[]{"name" };
    protected String[] search_filter = new String[]{"type", "gametypename"};

    @Override
    @GetMapping("/")
    public ServerResponse list(HttpServletRequest request,
                               @RequestParam(defaultValue = "") String search,
                               @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "15") Integer limit
                               ) {
        Page<GameServer> pages = new Page<>(page, limit);
        MyWrapper<GameServer> wrapperFactory = new MyWrapper<>();
        QueryWrapper<GameServer> wrapper = wrapperFactory.init(request, search, search_fields, search_filter);
        IPage<GameServer> iPage = baseSerivce.page(pages, wrapper);
        return ServerResponse.ofSuccess(iPage);
    }

    @GetMapping("/list2")
    public ServerResponse list2(HttpServletRequest request,
                                @RequestParam(defaultValue = "") String search,
                                @RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "15") Integer limit
    ) {
        Page<GameServerVo> pages = new Page<>(page, limit);
        IPage<GameServerVo> iPage = pages.setRecords(dao.select(pages, search));
        return ServerResponse.ofSuccess(iPage);
    }
}
