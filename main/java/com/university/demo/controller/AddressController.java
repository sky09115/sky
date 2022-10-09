package com.university.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.university.demo.entity.Address;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 地址
 * @author redcomet
 * @since 2022年9月15日
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService service;

    @PostMapping("/delete")
    public ServerResponse delete(@RequestBody Address record) {
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.eq("id",record.getId());
        return service.remove(wrapper) ? ServerResponse.ofSuccess("删除成功！") : ServerResponse.ofError("删除失败！");
    }

    @PostMapping("/add")
    public ServerResponse add(@RequestBody Address record) {
        boolean b = service.saveOrUpdate(record);
        if (b) {
            return ServerResponse.ofSuccess("操作成功", record);
        }
        return ServerResponse.ofError("操作失败!");
    }

    @GetMapping({"/search"})
    public ServerResponse searchUser(@RequestParam(value = "uid",required = false) Integer uid) {
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid);
        List<Address> addresses = service.list(wrapper);
        return ServerResponse.ofSuccess(addresses);
    }
}

