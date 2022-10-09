package com.university.demo.entity.response;

import lombok.Data;

@Data
public class BenefitItem {
    private Integer id;

    private String deptname;

    private String realname;

    private String item;

    private Double amount;
}
