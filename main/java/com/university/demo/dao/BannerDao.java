package com.university.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.university.demo.entity.Banner;
import com.university.demo.entity.Fav;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BannerDao extends BaseMapper<Banner> {

}
