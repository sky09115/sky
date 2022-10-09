package com.university.demo.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.entity.Admin;
import com.university.demo.entity.request.PasswordVO;
import com.university.demo.entity.request.UserLoginRequest;
import com.university.demo.service.AdminService;
import com.university.demo.service.impl.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lequal
 * @since 2020-03-06
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private TokenService tokenService;

    /**
     * 管理员登录
     * @param adminLoginRequest
     * @return
     */
    //@SysLog("测试")
    @PostMapping("/login")
    public ServerResponse login(@RequestBody UserLoginRequest adminLoginRequest) throws Exception{
        Map<String, Object> map = new HashMap();
        Admin admin = adminService.adminLogin(adminLoginRequest.getUsername(), adminLoginRequest.getPassword());
        if (admin != null){
            String token = tokenService.getToken(admin);
            admin.setToken(token);
            adminService.saveOrUpdate(admin);
            map.put("admin", admin);
            map.put("token", token);
            return ServerResponse.ofSuccess(map);
        }
        return ServerResponse.ofError("用户名或密码错误!");
    }

    @PostMapping("/logout")
    public ServerResponse logout(@RequestBody UserLoginRequest request) {
        Map<String, Object> map = new HashMap();
        Integer ret = adminService.adminLogout(request.getToken());
        System.out.println("ret==>" + ret);
        return ServerResponse.ofSuccess("注销成功");
    }

    @GetMapping("/info")
    public ServerResponse info(@RequestParam String token) {
        Map<String, Object> map = new HashMap();
        Admin admin = adminService.info(token);
        if (admin != null){
            map.put("userinfo", admin);
            return ServerResponse.ofSuccess(map);
        }
        return ServerResponse.ofError("token无效!");
    }
    /**
     * 管理员更新个人资料
     * @return
     */
    //@UserLoginToken
    @PostMapping("/modify")
    public ServerResponse modify(@RequestBody Admin admin) {
        return adminService.updateById(admin) ? ServerResponse.ofSuccess("更新成功！") : ServerResponse.ofError("更新失败！");
    }

    /**
     * 根据ID查询管理员信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ServerResponse queryAdmin(@PathVariable("id") Integer id) {
        return ServerResponse.ofSuccess(adminService.getById(id));
    }


    /**
     * 管理员修改密码
     * @param passwordVO
     * @return
     */
    @PostMapping("/password")
    public ServerResponse updatePass(@RequestBody PasswordVO passwordVO) {
        System.out.println(passwordVO + "======");
        QueryWrapper<Admin> wrapper = new QueryWrapper();
        wrapper.eq("id", passwordVO.getId());
        wrapper.eq("password", passwordVO.getOldPass());
        Admin admin = adminService.getOne(wrapper);
        if (admin == null) {
            return ServerResponse.ofError("旧密码错误");
        }
        // 否则进入修改密码流程
        admin.setPassword(passwordVO.getNewPass());
        boolean b = adminService.updateById(admin);
        if (b) {
            return ServerResponse.ofSuccess("密码修改成功");
        }
        return ServerResponse.ofError("密码更新失败");
    }


}

