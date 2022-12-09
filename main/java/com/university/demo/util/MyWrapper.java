package com.university.demo.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.university.demo.entity.User;

import javax.servlet.http.HttpServletRequest;

public class MyWrapper<T> {

    /**
     *
     * @param search_fields
     *        几种前缀：
     *              默认： 模糊匹配
     *              '=': 'iexact',
     * @param search_filter
     *
     * @return
     */
    public QueryWrapper<T>  init(HttpServletRequest request,
                                 String search,
                                 String[] search_fields, String[] search_filter){
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        for(String field: search_filter) {
            String real_field = getRealFeild(field);
            String f = request.getParameter(real_field);
            if(null!=f){
                if(field.startsWith("=")){
                    wrapper.eq(real_field, f);
                }else{
                    wrapper.like(real_field, f);
                }
            }
        }

        wrapper.and(w->{
            for(String field: search_fields){
                w.or().like(field, search);
            }
        });

        return wrapper;
    }

    public String getRealFeild(String field){
        return field.replace("=","");
    }
}
