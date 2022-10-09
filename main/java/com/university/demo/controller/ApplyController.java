package com.university.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.controller.base.BaseController;
import com.university.demo.entity.*;
import com.university.demo.entity.request.SearchRequest;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * shenqing
 * @author redcomet  QQ:626206333
 * @since 2022年1月15日
 */
@RestController
@RequestMapping("/apply")
public class ApplyController extends BaseController<Apply> {

    @Autowired
    private UserService userService;


    @PostMapping("/apply")
    public ServerResponse apply(@RequestBody Apply t) {
        User user = userService.getById(t.getUid());
        t.setBal(user.getBal());
        t.setRealname(user.getRealname());
        t.setIdno(user.getIdno());
        t.setStatus(0);

        boolean b = baseSerivce.saveOrUpdate(t);
        if (b) {
            return ServerResponse.ofSuccess("申请成功", t);
        }
        return ServerResponse.ofError("申请失败!");
    }



}

