package com.university.demo.entity.movie2;

import lombok.Data;

@Data
public class MovieUserRatingsVo extends MovieUserRatings {

    private String title;

    private String username;
}
