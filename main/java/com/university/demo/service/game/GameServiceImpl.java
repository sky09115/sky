package com.university.demo.service.game;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.game.GameDao;
import com.university.demo.entity.game.Game;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl extends ServiceImpl<GameDao, Game> implements GameService {

}
