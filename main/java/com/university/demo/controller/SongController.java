package com.university.demo.controller;

import com.university.demo.controller.base.BaseController;
import com.university.demo.entity.Apply;
import com.university.demo.entity.Song;
import com.university.demo.entity.User;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 */
@RestController
@RequestMapping("/song")
public class SongController extends BaseController<Song> {

}

