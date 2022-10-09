package com.university.demo.entity.request;

import lombok.Data;

@Data
public class SearchRequest {

    private  Integer group;

    private Integer userId;

    private String keyword;

    private String type;

    private String type2;

    private String type3;

    private String orderBy;

    private String role;
}
