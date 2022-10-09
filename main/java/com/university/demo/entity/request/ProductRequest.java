package com.university.demo.entity.request;

import lombok.Data;

/**
 * @author: 15760
 * @Date: 2020/4/4
 * @Descripe:
 */
@Data
public class ProductRequest {
    private Integer[] categoryID;

    private Integer categoryName;

    private Integer currentPage;

    private Integer pageSize;

    private String search;
}
