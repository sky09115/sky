package com.university.demo;

import com.university.demo.util.imagesearch.APPConfig;
import com.university.demo.util.imagesearch.AipImageSearch;
import com.university.demo.util.imagesearch.util.Base64Util;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class ImagesearchApplicationTests {

    AipImageSearch aipImageSearch = new AipImageSearch(APPConfig.APP_ID, APPConfig.API_KEY, APPConfig.SECRET_KEY);

    //测试身份证识别
    @Test
    public void recognizeIdCard() throws IOException {
        HashMap<String, String> options = new HashMap<String, String>();
        String id_card_side = "front";
        String fileName = "pic/front.png";

        byte[] pic = getPic(fileName);
        String base64Content = Base64Util.encode(pic);
        JSONObject result = aipImageSearch.idRecognize(base64Content, id_card_side, options);
        System.out.println(result);
    }

    //测试 上传图片
    @Test
    public void addImage() throws IOException {
        HashMap<String, String> options = new HashMap<String, String>();
        String fileName = "G:/uploads/pic/front.png";

        options.put("brief","1front.png");
        options.put("tags","1,1");
//        byte[] pic = getPic(fileName);
//        String base64Content = Base64Util.encode(pic);
        JSONObject result = aipImageSearch.similarAdd(fileName, options);
        System.out.println(result);
    }

    //测试 相似度图片查找
    @Test
    public void findSimilarImage() throws IOException {
        HashMap<String, String> options = new HashMap<String, String>();
        String fileName = "G:/uploads/pic/front.png";

//        options.put("brief","front.png");
//          options.put("tags","1,1");   //如果按照分类检索，如果分类不一样，结果不会出现的
//        byte[] pic = getPic(fileName);
//        String base64Content = Base64Util.encode(pic);
        JSONObject result = aipImageSearch.similarSearch(fileName, options);
        System.out.println(result);
    }

    //获取图片
    public byte[] getPic(String picName) throws IOException {
        InputStream pngInStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(picName);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];

        for (int readNum; (readNum = pngInStream.read(buf)) != -1; ) {
            bos.write(buf, 0, readNum);
        }

        byte[] bytes = bos.toByteArray();
        return bytes;
    }
}
