package com.university.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.anotation.SysLog;
import com.university.demo.controller.base.BaseController;
import com.university.demo.entity.Photo;
import com.university.demo.entity.User;
import com.university.demo.entity.request.PhotoData;
import com.university.demo.entity.request.SearchRequest;
import com.university.demo.entity.system.ServerResponse;

import com.university.demo.service.RateService;
import org.apache.commons.lang3.StringUtils;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作
 * @author redcomet
 * @since 2022年1月12日
 */
@RestController
@RequestMapping("/photo")
public class PhotoController extends BaseController<Photo> {



    @PostMapping("/addPhotoData")
    public ServerResponse add(@RequestBody PhotoData record) {
        boolean b = false;
        String type = record.getType();
        String urls = record.getUrls();
        Integer iid = record.getIid();
        // 如果iid 不存在，直接挂在最大的iid上
        if(iid == null){
//            iid = jobService.getMaxId();
        }

        if(StringUtils.isEmpty(type) || StringUtils.isEmpty(urls) || iid==null ){
            b = false;
        }else{
            //先清空iid相关照片表
            QueryWrapper<Photo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("iid", iid);
            baseSerivce.remove(queryWrapper);

            for (String _url : urls.split(",")) {
                Photo photo = new Photo();
                photo.setIid(iid);
                photo.setType(type);
                photo.setUrl(_url);
                baseSerivce.save(photo);
            }
            b = true;
        }
        
        if (b) {
            return ServerResponse.ofSuccess("处理成功", record);
        }
        return ServerResponse.ofError("处理失败!");
    }
}

