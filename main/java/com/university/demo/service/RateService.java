package com.university.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.university.demo.entity.News;
import com.university.demo.entity.Rate;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import java.util.List;

/**
 * @author redcomet
 * @since 2022-01-03
 */
public interface RateService extends IService<Rate> {

    //修改评分 value表示修改的变化值
    void updateRate(Integer uid, Integer iid, Double modifyValue);

    //设定评分
    void setupRate(Integer uid, Integer iid, Double rate);

    // 获取5个推荐
    List<RecommendedItem> getRecommendItemIds(Integer userId, Integer howMany);
}
