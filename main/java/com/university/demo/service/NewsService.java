package com.university.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.university.demo.entity.News;
import com.university.demo.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author redcomet
 * @since 2021-03-29
 */
public interface NewsService extends IService<News> {

    // var表示变化的数值，就是说点赞(+1)和取消点赞(-1)
    void doStar(Integer id, Integer var);

    void doThumb(Integer id, Integer var);
}
