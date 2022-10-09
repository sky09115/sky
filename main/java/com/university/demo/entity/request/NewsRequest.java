package com.university.demo.entity.request;

import lombok.Data;

@Data
public class NewsRequest {

    private String username;   //登录用户用户名

    private String web;

    private String keyword;

    private String title;

    private String begin;

    private String end;
}
