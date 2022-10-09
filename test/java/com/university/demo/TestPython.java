package com.university.demo;

import com.qianxinyao.analysis.jieba.keyword.Keyword;
import com.qianxinyao.analysis.jieba.keyword.TFIDFAnalyzer;
import com.university.demo.python.TransferPython.ToPython;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author: 15760
 * @Date: 2022/06/27
 * @Descripe: 测试情感分析
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class TestPython {

//
    @Autowired
    ToPython toPython;

    @Test
    public void testLstm() {
        String comment = "两个小孩子上幼儿园";
        String content = toPython.lstm(comment);
        System.out.println(content);
    }
}
