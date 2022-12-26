package com.university.demo.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * @author: 15760
 * @Date: 2020/3/4
 * @Descripe: 自动更新
 */

@Configuration
public class MyMetaObjectHandler implements MetaObjectHandler {

    // 自动插入公公字段
    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasSetter("create_time")&&getFieldValByName("create_time",metaObject)==null) {
            setInsertFieldValByName("create_time", LocalDateTime.now(), metaObject);
            //setInsertFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
    }

    // 自动更新公共字段
    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasSetter("update_time")&&getFieldValByName("update_time",metaObject)==null) {
            setUpdateFieldValByName("update_time", LocalDateTime.now(), metaObject);
        }
    }
}
