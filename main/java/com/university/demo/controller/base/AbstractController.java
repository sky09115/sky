package com.university.demo.controller.base;

import com.university.demo.entity.system.ServerResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;

/**
 * 声明泛型基类
 * created by redcomet
 * since 2022年1月12日11:17:35
 * @param <T>
 */
public abstract class AbstractController<T> {

    public abstract ServerResponse add(T t);

    public abstract ServerResponse delete(Serializable id);

    public abstract ServerResponse update(@PathVariable("id") Serializable id, T t);

    public abstract ServerResponse retrieve(Serializable id);

    public abstract ServerResponse list(String search, Integer page, Integer limit);

}
