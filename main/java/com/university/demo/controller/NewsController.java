package com.university.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.entity.*;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.dao.NewsDao;
import com.university.demo.entity.request.NewsRequest;
import com.university.demo.entity.request.SearchRequest;
import com.university.demo.entity.response.NewsVo;
import com.university.demo.python.TransferPython.ToPython;
import com.university.demo.service.*;
import com.university.demo.service.impl.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xm.tendency.word.HownetWordTendency;

import java.util.ArrayList;
import java.util.List;

/**
 * @author redcomet
 * @since 2022-06-24
 */
@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ThumbService thumbService;
    @Autowired
    private ConcernService concernService;
    @Autowired
    private StarService starService;
    @Autowired
    private RateService rateService;

    @Autowired
    private NewsDao newsDao;

    @Autowired
    ToPython toPython;

    @PostMapping("/modify")
    public ServerResponse modify(@RequestBody News news) {
        return newsService.updateById(news) ? ServerResponse.ofSuccess("更新成功！") : ServerResponse.ofError("更新失败！");
    }

    @GetMapping("/delete/{id}")
    public ServerResponse delete(@PathVariable("id") Integer id) {
        return newsService.removeById(id) ? ServerResponse.ofSuccess("删除成功！") : ServerResponse.ofError("删除失败！");
    }

    @GetMapping("/{id}")
    public ServerResponse query(@PathVariable("id") Integer id) {
        return ServerResponse.ofSuccess(newsService.getById(id));
    }

    // 根据【点赞-thumb】来进行倒序排序
    @GetMapping("/news/{page}")
    public ServerResponse querys(@PathVariable("page") Integer page,
                                       @RequestParam(defaultValue = "10") Integer limit) {
        Page<News> pages = new Page<>(page, limit);
        QueryWrapper<News> wrapper = new QueryWrapper<News>().orderByDesc("thumb");
        IPage<News> iPage = newsService.page(pages, wrapper);
        return ServerResponse.ofSuccess(iPage);
    }

    @PostMapping("/search2")
    public ServerResponse search2(@RequestBody SearchRequest params,
                                  @RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer limit) {
        QueryWrapper<News> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("time");
        wrapper.like(!StringUtils.isEmpty(params.getKeyword()), "title", params.getKeyword());
//                .eq(!StringUtils.isEmpty(params.getType()), "type", params.getType())
//                .eq("deleted", false);
        Page<News> pages = new Page<>(page, limit);
        IPage<News> iPage = newsService.page(pages, wrapper);
        if (page != null) {
            return ServerResponse.ofSuccess(iPage);
        }
        return ServerResponse.ofError("查询不到数据!");
    }

    @GetMapping({"/search/{keyword}", "/search/"})
    public ServerResponse search(@PathVariable(value = "keyword", required = false) String keyword, @RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer limit) {
        QueryWrapper<News> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("update_time");
        wrapper.like(!StringUtils.isEmpty(keyword), "title", keyword)
                .eq("deleted", false);
        Page<News> pages = new Page<>(page, limit);
        IPage<News> iPage = newsService.page(pages, wrapper);
        if (page != null) {
            return ServerResponse.ofSuccess(iPage);
        }
        return ServerResponse.ofError("查询不到数据!");
    }

    @PostMapping("/add")
    public ServerResponse add(@RequestBody News news) {
        news.setStar(0);
        news.setThumb(0);
        boolean b = newsService.save(news);
        if (b) {
            return ServerResponse.ofSuccess("添加成功", news);
        }
        return ServerResponse.ofError("添加失败!");
    }

    /* 给前端用的 */

    @GetMapping({"/fontsearch/{username}"})
    public ServerResponse fontsearch(@PathVariable(value = "username", required = false) String username, @RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer limit) {
        QueryWrapper<News> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("update_time");
        wrapper.like(!StringUtils.isEmpty(username), "author", username)
                .eq("deleted", false);
        Page<News> pages = new Page<>(page, limit);
        IPage<News> iPage = newsService.page(pages, wrapper);
        if (page != null) {
            return ServerResponse.ofSuccess(iPage);
        }
        return ServerResponse.ofError("查询不到数据!");
    }

    //model新闻接口
    @PostMapping({"/{username}"})
    public ServerResponse newsv1(@PathVariable(value = "username", required = false) String username, @RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer limit) {
        List<NewsVo> response = new ArrayList<>();
        QueryWrapper<News> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("time").orderByDesc("star").orderByDesc("thumb");
//        wrapper.eq("deleted", false);
//        wrapper.eq("status", true);
        Page<News> pages = new Page<>(page, limit);
        IPage<News> iPage = newsService.page(pages, wrapper);
        List<News> news = iPage.getRecords();
        news.forEach(news1 -> {
            NewsVo v = new NewsVo();
            BeanUtils.copyProperties(news1, v);
            QueryWrapper<Comment> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("remark", news1.getId());
            wrapper2.eq("deleted", false);
            List<Comment> comments = commentService.list(wrapper2);
            v.setComments(comments);
            //如果在用户登录状态下，去查询他有没有点赞或者关注过这个文章作者
            if (!"".equals(username)) {
                // 是否点赞
                QueryWrapper<Thumb> wrapper3 = new QueryWrapper<>();
                wrapper3.eq("nid", news1.getId()).eq("deleted", false)
                        .eq("sid", username);
                int thumbs = thumbService.count(wrapper3);
                if (thumbs > 0)
                    v.setIthumb(true);
                else
                    v.setIthumb(false);
                // 是否关注
                QueryWrapper<Star> wrapper4 = new QueryWrapper<>();
                wrapper4.eq("nid", news1.getId()).eq("deleted", false)
                        .eq("sid", username)
                        .eq("type","news");
                int stars = starService.count(wrapper4);
                if (stars > 0)
                    v.setIstar(true);
                else
                    v.setIstar(false);
            }
            response.add(v);
        });

        //前端需要返回分页参数
        IPage<NewsVo> voPage = new Page<>();
        voPage.setRecords(response);
        voPage.setSize(iPage.getSize());
        voPage.setPages(iPage.getPages());
        voPage.setCurrent(iPage.getCurrent());
        voPage.setTotal(iPage.getTotal());

        if (page != null) {
            return ServerResponse.ofSuccess(voPage);
        }
        return ServerResponse.ofError("查询不到数据!");
    }

    @PostMapping("/recommendNews")
    public ServerResponse recommendNews(@RequestBody NewsRequest params) {
        System.out.println("keyword:" + params.getKeyword());
        List<NewsVo> response = new ArrayList<>();
        QueryWrapper<News> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("update_time").orderByDesc("star").orderByDesc("thumb");
        wrapper.eq("keyword", params.getKeyword());
        wrapper.eq("deleted", false);
        wrapper.eq("status", true);
        Page<News> pages = new Page<>(1, 10);
        IPage<News> iPage = newsService.page(pages, wrapper);
        List<News> news = iPage.getRecords();
//        BeanUtils.copyProperties(news,response);
        news.forEach(news1 -> {
            NewsVo v = new NewsVo();
//            v.setId(news1.getId());
            BeanUtils.copyProperties(news1, v);
            QueryWrapper<Comment> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("remark", news1.getId());
            wrapper2.eq("deleted", false);
            List<Comment> comments = commentService.list(wrapper2);
            v.setComments(comments);
            //如果在用户登录状态下，去查询他有没有点赞或者关注过这个文章作者
            if (!"".equals(params.getUsername())) {
                // 是否点赞
                QueryWrapper<Thumb> wrapper3 = new QueryWrapper<>();
                wrapper3.eq("nid", news1.getId()).eq("deleted", false)
                        .eq("sid", params.getUsername());
                int thumbs = thumbService.count(wrapper3);
                if (thumbs > 0)
                    v.setIthumb(true);
                else
                    v.setIthumb(false);
                // 是否关注
                QueryWrapper<Concern> wrapper4 = new QueryWrapper<>();
                wrapper4.eq("rid", news1.getWeb()).eq("deleted", false)
                        .eq("sid", params.getUsername()).eq("type","news");
                int concerns = concernService.count(wrapper4);
                if (concerns > 0)
                    v.setIstar(true);
                else
                    v.setIstar(false);
            }
            response.add(v);
        });
        return ServerResponse.ofSuccess(response);
    }

    @PostMapping("/bookmarks")
    public ServerResponse bookmarks(@RequestBody NewsRequest params) {
        List<NewsVo> response = new ArrayList<>();
//        QueryWrapper<News> wrapper = new QueryWrapper<>();
//        //根据收藏 + 点赞来进行排序
//        wrapper.orderByDesc("star").orderByDesc("thumb");
//        wrapper.eq("deleted",false);
//        wrapper.eq("status",true);
//        Page<News> pages = new Page<>(1, 10);
//        IPage<News> iPage = newsService.page(pages, wrapper);
//        List<News> news = iPage.getRecords();
        List<News> news = newsDao.getBookMarks(params.getUsername());
//        BeanUtils.copyProperties(news,response);
        news.forEach(news1 -> {
            NewsVo v = new NewsVo();
//            v.setId(news1.getId());
            BeanUtils.copyProperties(news1, v);
            QueryWrapper<Comment> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("remark", news1.getId());
            wrapper2.eq("deleted", false);
            List<Comment> comments = commentService.list(wrapper2);
            v.setComments(comments);
            //如果在用户登录状态下，去查询他有没有点赞或者关注过这个文章作者
            if (!"".equals(params.getUsername())) {
                // 是否点赞
                QueryWrapper<Thumb> wrapper3 = new QueryWrapper<>();
                wrapper3.eq("nid", news1.getId()).eq("deleted", false)
                        .eq("sid", params.getUsername());
                int thumbs = thumbService.count(wrapper3);
                if (thumbs > 0)
                    v.setIthumb(true);
                else
                    v.setIthumb(false);
                // 是否关注
                QueryWrapper<Concern> wrapper4 = new QueryWrapper<>();
                wrapper4.eq("rid", news1.getWeb()).eq("deleted", false)
                        .eq("sid", params.getUsername()).eq("type","news");
                int concerns = concernService.count(wrapper4);
                if (concerns > 0)
                    v.setIstar(true);
                else
                    v.setIstar(false);
            }
            response.add(v);
        });
        return ServerResponse.ofSuccess(response);
    }

    /**
     * 综合搜索
     * 2021年12月28日
     */
    @PostMapping({"/searchx"})
    public ServerResponse searchx(@RequestBody NewsRequest params, @RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer limit) {
        List<NewsVo> response = new ArrayList<>();
        QueryWrapper<News> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("time");
        if (params != null) {
            wrapper.like(!StringUtils.isEmpty(params.getTitle()), "title", params.getTitle());
            wrapper.like(!StringUtils.isEmpty(params.getKeyword()), "keyword", params.getKeyword());

            wrapper.ge(!StringUtils.isEmpty(params.getBegin()), "time", params.getBegin());
            wrapper.le(!StringUtils.isEmpty(params.getEnd()), "time", params.getEnd());
        }

//        wrapper.eq("deleted", false);
        Page<News> pages = new Page<>(page, limit);
        IPage<News> iPage = newsService.page(pages, wrapper);
        List<News> news = iPage.getRecords();

        HownetWordTendency hownet = new HownetWordTendency();
        news.forEach(news1 -> {
            NewsVo v = new NewsVo();

            String title = v.getKeyword();
            double sim = hownet.getTendency(title);
            if(sim>0)
                v.setTend("正向");
            else if(sim<0)
                v.setTend("负向");
            else
                v.setTend("中性");
//            v.setId(news1.getId());
            BeanUtils.copyProperties(news1, v);
            QueryWrapper<Comment> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("remark", news1.getId());
            wrapper2.eq("deleted", false);
            List<Comment> comments = commentService.list(wrapper2);
            v.setComments(comments);
            //如果在用户登录状态下，去查询他有没有点赞或者关注过这个文章作者
            if (params != null && !StringUtils.isEmpty(params.getUsername())) {
                // 是否点赞
                QueryWrapper<Thumb> wrapper3 = new QueryWrapper<>();
                wrapper3.eq("nid", news1.getId()).eq("deleted", false)
                        .eq("sid", params.getUsername());
                int thumbs = thumbService.count(wrapper3);
                if (thumbs > 0)
                    v.setIthumb(true);
                else
                    v.setIthumb(false);
                // 是否收藏
                QueryWrapper<Star> wrapper4 = new QueryWrapper<>();
                wrapper4.eq("nid", news1.getId()).eq("deleted", false)
                        .eq("sid", params.getUsername()).eq("type","news");
                int stars = starService.count(wrapper4);
                if (stars > 0)
                    v.setIstar(true);
                else
                    v.setIstar(false);
            }
            response.add(v);
        });

        //前端需要返回分页参数
        IPage<NewsVo> voPage = new Page<>();
        voPage.setRecords(response);
        voPage.setSize(iPage.getSize());
        voPage.setPages(iPage.getPages());
        voPage.setCurrent(iPage.getCurrent());
        voPage.setTotal(iPage.getTotal());

        if (page != null) {
            return ServerResponse.ofSuccess(voPage);
        }
        return ServerResponse.ofError("查询不到数据!");
    }

    /**
     * 单个新闻接口
     * 2021年12月28日
     */
    @GetMapping({"/getOne/{id}"})
    public ServerResponse getOne(@PathVariable("id") Integer id, @RequestParam String username) {
        NewsVo vo = new NewsVo();
        News news = newsService.getById(id);
        BeanUtils.copyProperties(news, vo);
        QueryWrapper<Comment> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("remark", news.getId());
        wrapper2.eq("deleted", false);
        List<Comment> comments = commentService.list(wrapper2);
        vo.setComments(comments);
        //如果在用户登录状态下，去查询他有没有点赞或者关注过这个文章作者
        if(!StringUtils.isEmpty(username)) {
            // 是否点赞
            QueryWrapper<Thumb> wrapper3 = new QueryWrapper<>();
            wrapper3.eq("nid", news.getId()).eq("deleted", false)
                    .eq("sid", username);
            int thumbs = thumbService.count(wrapper3);
            if (thumbs > 0)
                vo.setIthumb(true);
            else
                vo.setIthumb(false);
            // 是否关注
            QueryWrapper<Star> wrapper4 = new QueryWrapper<>();
            wrapper4.eq("nid", news.getId()).eq("deleted", false)
                    .eq("sid", username).eq("type","news");
            int stars = starService.count(wrapper4);
            if (stars > 0)
                vo.setIstar(true);
            else
                vo.setIstar(false);
        }
        return ServerResponse.ofSuccess(vo);
    }

    // Mahout 基于协同过滤的推荐接口  基于User的皮尔逊相关系数
    @PostMapping({"/recommend"})
    public ServerResponse recommends(@RequestParam String username,@RequestBody SearchRequest query, @RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer limit) {
        List<NewsVo> records = new ArrayList();
        List<RecommendedItem> items = rateService.getRecommendItemIds(query.getUserId(), 5);
        for (RecommendedItem item : items) {
            NewsVo vo = new NewsVo();
            News news = newsService.getById(item.getItemID());
            BeanUtils.copyProperties(news, vo);

            QueryWrapper<Comment> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("remark", news.getId());
            wrapper2.eq("deleted", false);
            List<Comment> comments = commentService.list(wrapper2);
            vo.setComments(comments);
            //如果在用户登录状态下，去查询他有没有点赞或者关注过这个文章作者
            if(!StringUtils.isEmpty(username)) {
                // 是否点赞
                QueryWrapper<Thumb> wrapper3 = new QueryWrapper<>();
                wrapper3.eq("nid", news.getId()).eq("deleted", false)
                        .eq("sid", username);
                int thumbs = thumbService.count(wrapper3);
                if (thumbs > 0)
                    vo.setIthumb(true);
                else
                    vo.setIthumb(false);
                // 是否关注
                QueryWrapper<Star> wrapper4 = new QueryWrapper<>();
                wrapper4.eq("nid", news.getId()).eq("deleted", false)
                        .eq("sid", username).eq("type","news");
                int stars = starService.count(wrapper4);
                if (stars > 0)
                    vo.setIstar(true);
                else
                    vo.setIstar(false);
            }
            records.add(vo);
        }

        return ServerResponse.ofSuccess(records);
    }

    // 基于Python  item-cf 协同过滤算法的调用
    @PostMapping({"/recommend2"})
    public ServerResponse recommends2(@RequestParam String username,@RequestBody SearchRequest query, @RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer limit) {
        List<NewsVo> records = new ArrayList();

        String content = toPython.itemrec(String.valueOf(query.getUserId()));
        //转为json数据
        JSONArray jo = JSONObject.parseArray(content);
        for(int i=0;i<jo.size();i++){
            JSONObject  obj = jo.getJSONObject(i);

            NewsVo vo = new NewsVo();
            News news = newsService.getById(obj.getInteger("iid"));
            BeanUtils.copyProperties(news, vo);

            QueryWrapper<Comment> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("remark", news.getId());
            wrapper2.eq("deleted", false);
            List<Comment> comments = commentService.list(wrapper2);
            vo.setComments(comments);
            //如果在用户登录状态下，去查询他有没有点赞或者关注过这个文章作者
            if(!StringUtils.isEmpty(username)) {
                // 是否点赞
                QueryWrapper<Thumb> wrapper3 = new QueryWrapper<>();
                wrapper3.eq("nid", news.getId()).eq("deleted", false)
                        .eq("sid", username);
                int thumbs = thumbService.count(wrapper3);
                if (thumbs > 0)
                    vo.setIthumb(true);
                else
                    vo.setIthumb(false);
                // 是否关注
                QueryWrapper<Star> wrapper4 = new QueryWrapper<>();
                wrapper4.eq("nid", news.getId()).eq("deleted", false)
                        .eq("sid", username).eq("type","news");
                int stars = starService.count(wrapper4);
                if (stars > 0)
                    vo.setIstar(true);
                else
                    vo.setIstar(false);
            }
            records.add(vo);
        }

        return ServerResponse.ofSuccess(records);
    }

}

