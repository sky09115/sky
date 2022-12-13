package com.university.demo.controller.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.university.demo.entity.system.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 实现泛型父类
 * created by 麦克斯韦
 * since 2022-12-08
 * @param <T>
 */
public class BaseController<T> extends AbstractController<T>{

    @Autowired(required = false)
    protected IService<T> baseSerivce;

    @Override
    @PostMapping("/")
    public ServerResponse add(@RequestBody T t) {
        boolean b = baseSerivce.saveOrUpdate(t);
        if (b) {
            return ServerResponse.ofSuccess("添加/更新成功", t);
        }
        return ServerResponse.ofError("添加/更新失败!");
    }

    @Override
    @DeleteMapping("/{id}")
    public ServerResponse delete(@PathVariable("id") Serializable id) {
        return baseSerivce.removeById(id) ? ServerResponse.ofSuccess("删除成功！") : ServerResponse.ofError("删除失败！");
    }

    @Override
    @PutMapping("/{id}")
    public ServerResponse update(@PathVariable("id") Serializable id, @RequestBody T t) {
        return baseSerivce.updateById(t) ? ServerResponse.ofSuccess("更新成功！") : ServerResponse.ofError("更新失败！");
    }

    @Override
    @GetMapping("/{id}")
    public ServerResponse retrieve(@PathVariable("id") Serializable id) {
        return ServerResponse.ofSuccess(baseSerivce.getById(id));
    }

    @Override
    @GetMapping("/")
    public ServerResponse list(HttpServletRequest request,
                               @RequestParam(defaultValue = "") String search,
                               @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "15") Integer limit) {
        Page<T> pages = new Page<>(page, limit);
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        IPage<T> iPage = baseSerivce.page(pages, wrapper);
        return ServerResponse.ofSuccess(iPage);
    }
}
