package com.university.demo.service.game;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.university.demo.dao.game.GameGoodDao;
import com.university.demo.dao.game.GameServerDao;
import com.university.demo.entity.game.GameGood;
import com.university.demo.entity.game.GameServer;
import org.springframework.stereotype.Service;

@Service
public class GameServerServiceImpl extends ServiceImpl<GameServerDao, GameServer> implements GameServerService {

}
