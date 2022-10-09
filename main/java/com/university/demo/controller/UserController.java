package com.university.demo.controller;

import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.controller.api.service.ApiLoginService;
import com.university.demo.controller.api.util.Result;
import com.university.demo.controller.api.util.ResultGenerator;
import com.university.demo.dao.SmsCodeDao;
import com.university.demo.dao.UserDao;
import com.university.demo.entity.SmsCode;
import com.university.demo.entity.Song;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.entity.system.ServiceResultEnum;
import com.university.demo.entity.system.SysConstant;
import com.university.demo.anotation.SysLog;
import com.university.demo.anotation.UserLoginToken;
import com.university.demo.entity.PassLog;
import com.university.demo.entity.User;
import com.university.demo.entity.request.PasswordVO;
import com.university.demo.entity.request.SearchRequest;
import com.university.demo.entity.request.UserLoginRequest;
import com.university.demo.service.PassLogService;
import com.university.demo.service.SongService;
import com.university.demo.service.UserService;
import com.university.demo.service.impl.TokenService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * @author redcomet
 * @since 2021-04-02
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PassLogService passLogService;
    @Autowired
    private ApiLoginService apiLoginService;

    @Autowired
    private SmsCodeDao dao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SongService songService;

    //@SysLog("测试")
    @PostMapping("/login")
    @SysLog(value=SysConstant.LOGIN)
    public ServerResponse login(@RequestBody UserLoginRequest adminLoginRequest) throws Exception{
        Map<String, Object> map = new HashMap();
        User user = userService.login(adminLoginRequest.getUsername(), adminLoginRequest.getPassword());
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

    @GetMapping("/userInfo/{id}")
    public ServerResponse personInfo(@PathVariable("id") Integer id) {
        Map<String, Object> map = new HashMap();
        User user = userService.info(Integer.valueOf(id));
        if (user != null){
            map.put("userinfo", user);
            return ServerResponse.ofSuccess(map);
        }
        return ServerResponse.ofError("查询失败!");
    }


    @GetMapping("/get/{id}")
    public ServerResponse get(@PathVariable("id") Integer id) {
        Map<String, Object> map = new HashMap();
        User user = userService.info(Integer.valueOf(id));
        if (user != null){
//            map.put("userinfo", user);
            return ServerResponse.ofSuccess(user);
        }
        return ServerResponse.ofError("查询失败!");
    }

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
    /**
     * 管理员更新个人资料
     * @return
     */
    @UserLoginToken
    @PostMapping("/modify")
    public ServerResponse modify(@RequestBody User user) {
        if(user.getDeleted()!=null && user.getDeleted() == 1)
            return userService.removeById(user) ? ServerResponse.ofSuccess("更新成功！") : ServerResponse.ofError("更新失败！");
        else
            return userService.updateById(user) ? ServerResponse.ofSuccess("更新成功！") : ServerResponse.ofError("更新失败！");
    }

    /**
     * 根据ID查询管理员信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ServerResponse queryUser(@PathVariable("id") Integer id) {
        return ServerResponse.ofSuccess(userService.getById(id));
    }

    @GetMapping("/users/{page}")
    public ServerResponse queryUsers(@PathVariable("page") Integer page,
                                       @RequestParam(defaultValue = "10") Integer limit) {
        Page<User> pages = new Page<>(page, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("deleted",false).eq("user_type",true);
        IPage<User> iPage = userService.page(pages, wrapper);
        return ServerResponse.ofSuccess(iPage);
    }

    @PostMapping("/search2")
    public ServerResponse search2(@RequestBody SearchRequest params,
                                  @RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "50") Integer limit) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        wrapper.like(!StringUtils.isEmpty(params.getKeyword()), "realname", params.getKeyword())
                .eq("deleted",false);
        if(params.getGroup()!=null)
            wrapper.eq(params.getGroup()!=0, "group_id",params.getGroup());
        if(params.getRole()!=null)
            wrapper.eq( "roles",params.getRole());

        Page<User> pages = new Page<>(page, limit);
        IPage<User> iPage = userService.page(pages, wrapper);
        if (page != null) {
            return ServerResponse.ofSuccess(iPage);
        }
        return ServerResponse.ofError("查询不到数据!");
    }

    @GetMapping({"/search/{keyword}","/search/"})
    public ServerResponse searchUser(@PathVariable(value = "keyword",required = false) String keyword, @RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer limit) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("update_time");
        wrapper.like(!StringUtils.isEmpty(keyword), "realname", keyword)
                .eq("deleted",false);
        Page<User> pages = new Page<>(page, limit);
        IPage<User> iPage = userService.page(pages, wrapper);
        if (page != null) {
            return ServerResponse.ofSuccess(iPage);
        }
        return ServerResponse.ofError("查询不到数据!");
    }

    @PostMapping("/resetpass/{id}")
    public ServerResponse resetpass(@PathVariable Integer id) {
        User t = new User();
        t.setId(id);
        t.setPassword("123456");
        boolean b = userService.saveOrUpdate(t);
        if(b) {
            return ServerResponse.ofSuccess("成功！");
        }
        return ServerResponse.ofError("失败！");
    }

    /**
     * 管理员修改密码
     * @param passwordVO
     * @return
     */
    @PostMapping("/password")
    @SysLog(value= SysConstant.MODIFY_PASSWORD)
    public ServerResponse updatePass(@RequestBody PasswordVO passwordVO) {
        Integer isExist = passLogService.countPass(passwordVO.getId());
        if(isExist>0)
            return ServerResponse.ofError("修改失败！半年内只能修改一次密码");

        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("id", passwordVO.getId());
        wrapper.eq("password", passwordVO.getOldPass());
        User user = userService.getOne(wrapper);
        if (user == null) {
            return ServerResponse.ofError("旧密码错误");
        }

        if (!passwordVO.getNewPass().equals(passwordVO.getRePass())){
            return ServerResponse.ofError("两次密码不一致");
        }

        // 否则进入修改密码流程
        user.setPassword(passwordVO.getNewPass());
        boolean b = userService.updateById(user);
        if (b) {
            PassLog pl = new PassLog();
            pl.setUserId(passwordVO.getId());
            passLogService.save(pl);
            return ServerResponse.ofSuccess("密码修改成功");
        }
        return ServerResponse.ofError("密码更新失败");
    }

    @PostMapping("/add")
    public ServerResponse addUser(@RequestBody User user) {
        user.setPassword("123456");
//        user.setUserType(1);
        user.setRemark("China");
        boolean b = userService.save(user);
        if (b) {
            return ServerResponse.ofSuccess("添加成功", user);
        }
        return ServerResponse.ofError("添加失败!");
    }

    @PostMapping("/register/{code}")
    @ApiOperation(value = "用户注册", notes = "")
    public Result register(@RequestBody @Valid User mallUserRegisterParam, @PathVariable String code) {
        //验证下验证码
        QueryWrapper<SmsCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("expire_time");
        queryWrapper.eq("phone", mallUserRegisterParam.getPhone());
        SmsCode code2 = dao.selectList(queryWrapper).get(0);
        if(!code2.getCode().equals(code))
            return ResultGenerator.genFailResult("验证码不符!");

        String registerResult = apiLoginService.register(mallUserRegisterParam);
        //注册成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(registerResult)) {
            // 把电话也邮箱也更新一下
            return ResultGenerator.genSuccessResult();
        }
        //注册失败
        return ResultGenerator.genFailResult(registerResult);
    }

    @GetMapping("/dev")
    public ServerResponse dev() {
        return ServerResponse.ofError("15:07.......!");
    }

    // 实名认证
    @PostMapping(value = "/idconfirm")
    @SysLog(value= SysConstant.IDCONFIRM)
    public ServerResponse idconfirm(@RequestBody User user) {
        User u = userService.getById(user);
        u.setIdno(user.getIdno());
        userService.updateById(u);
        // 更新密码
        return ServerResponse.ofSuccess("认证成功");
    }

    //用户历史列表
    @GetMapping ("/history/all")
    public ServerResponse getHistory(@RequestParam(defaultValue = "1") Integer uid) {
        String histories_str = userDao.getHistoryAll(uid);
        String [] histories = histories_str.split(",");
        List<Song> songList = new ArrayList<>();
        for(int x=0; x<6 ; x++){
            Song song = songService.getById(histories[x]);
            songList.add(song);
        }
        return ServerResponse.ofSuccess(songList);
    }

    @GetMapping ("/history/add")
    public ServerResponse addHistory(@RequestParam(defaultValue = "1") Integer uid,
                                     @RequestParam(defaultValue = "1") Integer iid) {
        String histories_str = userDao.getHistoryAll(uid);
        String [] histories = histories_str.split(",");
        List<String> hisList = new ArrayList<>();
        List<String> oHisList =  Arrays.asList(histories);
        for (String s : oHisList) {
            hisList.add(s);
        }

//        System.out.println(iid);
//        System.out.println(Integer.valueOf(hisList.get(0)));

        if(iid.equals(Integer.valueOf(hisList.get(0)))){

            return ServerResponse.ofSuccess("无更新");
        }else{
            hisList.remove(5);
            hisList.remove(4);
            hisList.add(0, String.valueOf(iid));
            hisList.add(5, "1");
            userDao.updateHistory(uid, String.join(",", hisList));
            return ServerResponse.ofSuccess("无更新");
        }

    }
}

