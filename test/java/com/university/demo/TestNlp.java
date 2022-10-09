package com.university.demo;

import org.junit.jupiter.api.Test;
import org.xm.Similarity;
import org.xm.tendency.word.HownetWordTendency;

/**
 * @author: 15760
 * @Date: 2022/06/27
 * @Descripe: 测试情感分析
 */

public class TestNlp {

    @Test
    public void test() {
        double result = Similarity.cilinSimilarity("电动车", "自行车");
        System.out.println(result);

//        String word = "混蛋";
//        String word = "不行的";

        HownetWordTendency hownet = new HownetWordTendency();
        String word = "美好";
        double sim = hownet.getTendency(word);
        System.out.println(word + ":" + sim);
        System.out.println("混蛋:" + hownet.getTendency("混蛋"));
    }
}
