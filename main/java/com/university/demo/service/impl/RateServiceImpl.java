package com.university.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.university.demo.dao.RateDao;
import com.university.demo.entity.Rate;
import com.university.demo.service.RateService;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RateServiceImpl extends ServiceImpl<RateDao, Rate> implements RateService {

    @Override
    public void updateRate(Integer uid, Integer iid, Double modifyValue) {
        QueryWrapper<Rate> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid).eq("iid", iid);
        List<Rate> rates = baseMapper.selectList(wrapper);
        if(rates == null){
            Rate newRate = new Rate();
            newRate.setUid(uid);
            newRate.setIid(iid);
            newRate.setRate(modifyValue);
            baseMapper.insert(newRate);
        }else{
            Rate oldRate = rates.get(0);
            Double old = oldRate.getRate();
            oldRate.setRate(old + modifyValue);
            baseMapper.updateById(oldRate);
        }
    }

    @Override
    public void setupRate(Integer uid, Integer iid, Double rate) {
        QueryWrapper<Rate> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid).eq("iid", iid);
        List<Rate> rates = baseMapper.selectList(wrapper);
        if(rates == null || rates.size()==0){
            Rate newRate = new Rate();
            newRate.setUid(uid);
            newRate.setIid(iid);
            newRate.setRate(rate);
            baseMapper.insert(newRate);
        }else{
            Rate oldRate = rates.get(0);
            oldRate.setRate(rate);
            baseMapper.updateById(oldRate);
        }
    }

    @Override
    public List<RecommendedItem> getRecommendItemIds(Integer userId , Integer howMany) {
        MysqlDataSource datasource = new MysqlDataSource();
        datasource.setUrl("jdbc:mysql://localhost:3306/news?useSSL=false&serverTimezone=UTC&characterEncoding=utf-8&serverTimezone=Asia/Shanghai");
//        datasource.setServerName("localhost");
        datasource.setUser("root");
        datasource.setPassword("123456");
//        datasource.setDatabaseName("job");
        JDBCDataModel dataModel = new MySQLJDBCDataModel(datasource, "tb_rate",
                "uid", "iid", "rate", null);

        UserSimilarity similarity;
        try {
            similarity = new PearsonCorrelationSimilarity(dataModel);
            UserNeighborhood neighbourhood = new NearestNUserNeighborhood(2,
                    similarity, dataModel);

            Recommender recommender = new GenericUserBasedRecommender(
                    dataModel, neighbourhood, similarity);
            long start = System.currentTimeMillis();
            List<RecommendedItem> recommendations = recommender.recommend(
                    userId, howMany);
            for (RecommendedItem recommendation : recommendations) {
                System.out.println(recommendation);
            }
            long stop = System.currentTimeMillis();
            System.out.println("Took: " + (stop - start) + " millis");
            return recommendations;
        } catch (TasteException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
