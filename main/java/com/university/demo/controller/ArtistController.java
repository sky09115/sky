package com.university.demo.controller;

import com.university.demo.controller.base.BaseController;
import com.university.demo.entity.Artist;
import com.university.demo.entity.Song;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 */
@RestController
@RequestMapping("/artist")
public class ArtistController extends BaseController<Artist> {

}

