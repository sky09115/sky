package com.university.demo.controller.baidu;


import com.university.demo.entity.system.ServerResponse;
import com.university.demo.service.impl.FileService;
import com.university.demo.util.imagesearch.APPConfig;
import com.university.demo.util.imagesearch.AipImageSearch;
import com.university.demo.util.imagesearch.util.Base64Util;
import com.university.demo.util.imagesearch.util.Util;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;

@RequestMapping("/baidu")
@RestController
public class BaiduController {

    private static final Logger logger = LoggerFactory.getLogger(BaiduController.class);

    AipImageSearch aipImageSearch = new AipImageSearch(APPConfig.APP_ID, APPConfig.API_KEY, APPConfig.SECRET_KEY);

    @Autowired
    private FileService fileService;

    // 百度身份证识别
    @PostMapping("/idRecognize")
    public ServerResponse idRecognize(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = fileService.storeFile(file);;
        String id_card_side = "front";
        HashMap<String, String> options = new HashMap<String, String>();
        // windows
//        String realFile = fileService.getFileStorePath() + "\\" + fileName;
        // Macbook
        String realFile = fileService.getFileStorePath() + "/" + fileName;

        byte[] data = Util.readFileByBytes(realFile);
//        byte[] pic = getPic(realFile);
        String base64Content = Base64Util.encode(data);
        JSONObject result = aipImageSearch.idRecognize( base64Content, id_card_side, options);
//        Stringresult.get("");
        JSONObject words_result = result.getJSONObject("words_result");
        JSONObject idno = words_result.getJSONObject("公民身份号码");

        // 除了返回一个身份证号，还需要返回图片的地址
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/file/downloadFile/" )
                .path(fileName)
                .toUriString();
        HashMap<String, String> re = new HashMap<String, String>();
        re.put("fileDownloadUri",fileDownloadUri);
        re.put("idno",idno.getString("words"));
        re.put("name",words_result.getJSONObject("姓名").getString("words"));
        re.put("nation",words_result.getJSONObject("民族").getString("words"));
        re.put("addr",words_result.getJSONObject("住址").getString("words"));
        re.put("birth",words_result.getJSONObject("出生").getString("words"));
        re.put("sex",words_result.getJSONObject("性别").getString("words"));

        return ServerResponse.ofSuccess(re);
    }
}