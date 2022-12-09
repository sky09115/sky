package com.university.demo.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.university.demo.entity.User;

import javax.servlet.http.HttpServletRequest;

public class MyWrapper<T> {

    public QueryWrapper<T>  init(HttpServletRequest request,
                                 String search,
                                 String[] search_fields, String[] search_filter){
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        for(String field: search_filter) {
            String f = request.getParameter(field);
            if(null!=f)
                wrapper.eq(field, f);
        }

        wrapper.and(w->{
            for(String field: search_fields){
                w.or().like(field, search);
            }
        });

        return wrapper;
    }

}
