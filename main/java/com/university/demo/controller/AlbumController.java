package com.university.demo.controller;

import com.university.demo.controller.base.BaseController;
import com.university.demo.entity.Album;
import com.university.demo.entity.Artist;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 */
@RestController
@RequestMapping("/album")
public class AlbumController extends BaseController<Album> {

}

