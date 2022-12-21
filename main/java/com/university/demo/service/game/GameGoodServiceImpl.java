package com.university.demo.service.game;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.game.GameDao;
import com.university.demo.dao.game.GameGoodDao;
import com.university.demo.entity.game.Game;
import com.university.demo.entity.game.GameGood;
import org.springframework.stereotype.Service;

@Service
public class GameGoodServiceImpl extends ServiceImpl<GameGoodDao, GameGood> implements GameGoodService {

}
