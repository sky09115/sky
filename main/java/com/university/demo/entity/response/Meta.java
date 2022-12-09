package com.university.demo.entity.response;

import lombok.Data;

@Data
public class Meta {
    private String roles;

    private String title;

    private String icon;

    private Boolean affix;

    private Boolean hidden;
}
