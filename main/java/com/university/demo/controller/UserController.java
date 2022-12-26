package com.university.demo.controller;


import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.anotation.SysLog;
import com.university.demo.controller.base.BaseController;
import com.university.demo.controller.base.MyWrapper;
import com.university.demo.entity.User;
import com.university.demo.entity.response.RouteVo;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.entity.system.SysConstant;
import com.university.demo.service.RouteService;
import com.university.demo.service.UserService;
import com.university.demo.service.impl.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 麦克斯韦
 * @since  2022-12-08
 * @说明： 1. list 支持一个搜索参数search，search_fields 支持多字段模糊查询
 *        2. list search_filter为拓展的get请求参数,配置了前台可以不传,不影响查询结果
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User> {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    protected String[] search_fields = new String[]{"username", "realname"};
    protected String[] search_filter = new String[]{"roles", "=major"};

    @Override
    @GetMapping("/")
    public ServerResponse list(HttpServletRequest request,
                               @RequestParam(defaultValue = "") String search,
                               @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "15") Integer limit
                               ) {
        Page<User> pages = new Page<>(page, limit);
        MyWrapper<User> wrapperFactory = new MyWrapper<>();
        QueryWrapper<User> wrapper = wrapperFactory.init(request, search, search_fields, search_filter);
        IPage<User> iPage = baseSerivce.page(pages, wrapper);
        return ServerResponse.ofSuccess(iPage);
    }

    // 登录接口
    @PostMapping("/login")
    @SysLog(value= SysConstant.LOGIN)
    public ServerResponse login(@RequestBody User loginForm) throws Exception{
        Map<String, Object> map = new HashMap();
        User user = userService.login(loginForm.getUsername(), loginForm.getPassword());
        if (user != null){
            String token = tokenService.getToken(user);
            map.put("id", user.getId());
            map.put("username", user.getUsername());
            map.put("user", user);
            map.put("token", token);
            return ServerResponse.ofSuccess(map);
        }
        return ServerResponse.ofError("用户名或密码错误!");
    }

    // element端是会拦截请求并且通过token来获取用户的info
    @GetMapping("/info")
    public ServerResponse info(HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        String token = request.getHeader("token");
        String userId = JWT.decode(token).getAudience().get(0);
        User user = userService.info(Integer.valueOf(userId));
        if (user != null){
            map.put("userinfo", user);
            return ServerResponse.ofSuccess(map);
        }
        return ServerResponse.ofError("查询失败!");
    }

    @PostMapping(value = "/idconfirm")
    @SysLog(value= SysConstant.IDCONFIRM)
    public ServerResponse idconfirm(@RequestBody User user) {
        User u = userService.getById(user);
        u.setIdno(user.getIdno());
        u.setRealname(user.getRealname());
        userService.updateById(u);
        // 更新密码
        return ServerResponse.ofSuccess("认证成功");
    }
}

