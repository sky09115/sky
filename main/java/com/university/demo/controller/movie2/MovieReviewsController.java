package com.university.demo.controller.movie2;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.controller.base.BaseController;
import com.university.demo.controller.base.MyWrapper;
import com.university.demo.dao.movie2.MovieReviewsDao;
import com.university.demo.dao.movie2.MovieUserRatingsDao;
import com.university.demo.entity.movie2.ImdbRatingsVo;
import com.university.demo.entity.movie2.MovieDetail;
import com.university.demo.entity.movie2.MovieReviews;
import com.university.demo.entity.movie2.MovieReviewsVo;
import com.university.demo.entity.system.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * @author 麦克斯韦
 * @since  2022-12-13
 * @说明： 1. list 支持一个搜索参数search，search_fields 支持多字段模糊查询
 *        2. list search_filter为拓展的get请求参数,配置了前台可以不传,不影响查询结果
 */
@RestController
@RequestMapping("/review")
public class MovieReviewsController extends BaseController<MovieReviews> {

    @Autowired
    protected MovieReviewsDao dao;
    protected String[] search_fields = new String[]{"douban_id"};
    protected String[] search_filter = new String[]{};

    @Override
    @GetMapping("/")
    public ServerResponse list(HttpServletRequest request,
                               @RequestParam(defaultValue = "") String search,
                               @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "15") Integer limit
                               ) {
        Page<MovieReviews> pages = new Page<>(page, limit);
        MyWrapper<MovieReviews> wrapperFactory = new MyWrapper<>();
        QueryWrapper<MovieReviews> wrapper = wrapperFactory.init(request, search, search_fields, search_filter);
        IPage<MovieReviews> iPage = baseSerivce.page(pages, wrapper);
        return ServerResponse.ofSuccess(iPage);
    }

    @GetMapping("/list2")
    public ServerResponse list2(HttpServletRequest request,
                                @RequestParam(defaultValue = "") String search,
                                @RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "15") Integer limit
    ) {
        Page<MovieReviewsVo> pages = new Page<>(page, limit);
        IPage<MovieReviewsVo> iPage = pages.setRecords(dao.select(pages, search));
        return ServerResponse.ofSuccess(iPage);
    }
}

