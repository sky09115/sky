package com.university.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.controller.base.BaseController;
import com.university.demo.entity.Forecast;
import com.university.demo.entity.Weather;
import com.university.demo.entity.request.SearchRequest;
import com.university.demo.entity.system.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 */
@RestController
@RequestMapping("/forecast")
public class ForecastController extends BaseController<Forecast> {
    @PostMapping("/search")
    public ServerResponse search2(@RequestBody SearchRequest params,
                                  @RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer limit) {
        QueryWrapper<Forecast> wrapper = new QueryWrapper<>();
        wrapper.like(!StringUtils.isEmpty(params.getKeyword()), "city", params.getKeyword());
        wrapper.like(!StringUtils.isEmpty(params.getType()), "update_time", params.getType());
        wrapper.orderByAsc("update_time");
        Page<Forecast> pages = new Page<>(page, limit);
        IPage<Forecast> iPage = baseSerivce.page(pages, wrapper);
        if (page != null) {
            return ServerResponse.ofSuccess(iPage);
        }
        return ServerResponse.ofError("查询不到数据!");
    }
}
