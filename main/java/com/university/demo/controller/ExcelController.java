package com.university.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.entity.User;
import com.university.demo.entity.UserXls;
import com.university.demo.service.UserService;
import com.university.demo.util.excel.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/excel")
@RestController
public class ExcelController {

    private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);

//    @Autowired
//    private FileService fileService;

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ServerResponse upload1(@RequestParam("file") MultipartFile file){
        List<Object> datas = ExcelUtil.readExcel(file, new UserXls());

        for(Object obj: datas){
            System.out.println(obj);

            User user = new User();
            BeanUtils.copyProperties(obj, user);  //复制到User对象里
            if(user.getId() == null || StringUtils.isEmpty(user.getUsername())){
                continue;
            }
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("username", user.getUsername());
            User exist = userService.getOne(wrapper);
            if(exist==null){
//                user.setUserType(1);
                user.setPassword("123456");
                userService.save(user);
            }else{
                user.setId(exist.getId());  //上传的ID可能不对，所以重新设置下
//                System.out.println("user:"  + user);
                userService.updateById(user); //利用mybatis-plus去更新，不会更新掉空字段
            }
        }

        return ServerResponse.ofSuccess("上传EXCEL成功！");
    }
}