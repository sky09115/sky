package com.university.demo.util;

public class RandomUtil {

    public static double randomDouble(double x, double y){
        return  x + Math.random() * (y - x);
    }
}
