package com.university.demo;

import com.qianxinyao.analysis.jieba.keyword.Keyword;
import com.qianxinyao.analysis.jieba.keyword.TFIDFAnalyzer;
import org.junit.jupiter.api.Test;
import org.xm.Similarity;
import org.xm.tendency.word.HownetWordTendency;

import java.util.List;

/**
 * @author: 15760
 * @Date: 2022/06/27
 * @Descripe: 测试情感分析
 */

public class TestJieba {

    @Test
    public void test() {
        String content="孩子上了幼儿园 安全防拐教育要做好";
        int topN=5;
        TFIDFAnalyzer tfidfAnalyzer=new TFIDFAnalyzer();
        List<Keyword> list=tfidfAnalyzer.analyze(content,topN);
        for(Keyword word:list)
            System.out.println(word.getName()+":"+word.getTfidfvalue()+",");
        // 防拐:0.1992,幼儿园:0.1434,做好:0.1065,教育:0.0946,安全:0.0924
    }
}
