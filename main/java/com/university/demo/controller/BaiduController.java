package com.university.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.university.demo.entity.system.ServerResponse;
import com.university.demo.entity.Pic;
import com.university.demo.entity.response.SimilarImage;
import com.university.demo.entity.response.UploadFileResponse;
import com.university.demo.service.FileService;
import com.university.demo.service.PicService;
import com.university.demo.util.imagesearch.APPConfig;
import com.university.demo.util.imagesearch.AipImageSearch;
import com.university.demo.util.imagesearch.util.Base64Util;
import com.university.demo.util.imagesearch.util.Util;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/baidu")
@RestController
public class BaiduController {

    private static final Logger logger = LoggerFactory.getLogger(BaiduController.class);

    AipImageSearch aipImageSearch = new AipImageSearch(APPConfig.APP_ID, APPConfig.API_KEY, APPConfig.SECRET_KEY);

    @Autowired
    private FileService fileService;

    @Autowired
    private PicService picService;

    //上传到百度相似图片识别
    @PostMapping("/uploadSimilarFile")
    public UploadFileResponse uploadSimilarFile(@RequestParam("file") MultipartFile file){
        String fileName = fileService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        logger.info("fileDownloadUri:" + fileDownloadUri);

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("brief",fileName);
        options.put("tags","1,1");

        JSONObject result = aipImageSearch.similarAdd(fileService.getFileStorePath() + "\\" + fileName, options);
        logger.info("baidu_result:" +result);

        if(!result.isNull("error_code")){
            //报错了
            String cont_sign = (String) result.get("cont_sign");
            Pic pic = new Pic();
            pic.setName(fileName);
            pic.setUrl(fileDownloadUri);
            pic.setContSign(cont_sign);
            picService.save(pic);
        }else{
            String cont_sign = (String) result.get("cont_sign");
            Pic pic = new Pic();
            pic.setName(fileName);
            pic.setUrl(fileDownloadUri);
            pic.setContSign(cont_sign);
            picService.save(pic);
        }

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @GetMapping("/deleteSimilarFile")
    public ServerResponse deleteSimilarFile(@RequestParam String fileName){
        HashMap<String, String> options = new HashMap<String, String>();
        JSONObject result = aipImageSearch.similarDeleteByImage(fileService.getFileStorePath() + "\\" + fileName, options);
        logger.info("baidu_result:" +result);

        if(!result.isNull("error_code")){
            //报错了
        }else{
            QueryWrapper<Pic> wrapper = new QueryWrapper<>();
            wrapper.eq("name", fileName);
            picService.remove(wrapper);
        }
        return ServerResponse.ofSuccess("删除成功");
    }

    @GetMapping("/pics/{page}")
    public ServerResponse querys(@PathVariable("page") Integer page,
                                 @RequestParam(defaultValue = "200") Integer limit) {
        Page<Pic> pages = new Page<>(page, limit);
        QueryWrapper<Pic> wrapper = new QueryWrapper<Pic>().eq("deleted",false);
        IPage<Pic> iPage = picService.page(pages, wrapper);
        return ServerResponse.ofSuccess(iPage);
    }

    @GetMapping("/searchSimilar")
    public ServerResponse searchSimilar(@RequestParam String fileName) {
        HashMap<String, String> options = new HashMap<String, String>();
        List<SimilarImage> sis = new ArrayList<>();
        JSONObject result = aipImageSearch.similarSearch(fileService.getFileStorePath() + "\\" + fileName, options);
        DecimalFormat df = new DecimalFormat("#.00");

        JSONArray array = result.getJSONArray("result");
//        List<SimilarImage> list = JSONObject.toList(array.toJSONString(), SimilarImage.class);
        for(int i=0;i<array.length();i++){
            SimilarImage s = new SimilarImage();
            JSONObject jb = array.getJSONObject(i);
            if(fileName.equals(jb.getString("brief")))
                continue;
            s.setBrief("http://localhost:8080/downloadFile/" + jb.getString("brief"));
            s.setScore(Double.valueOf(df.format(jb.getDouble("score")*100)));
            s.setCont_sign(jb.getString("cont_sign"));
            sis.add(s);
        };
        return ServerResponse.ofSuccess(sis);
    }

    @PostMapping("/idRecognize")
    public ServerResponse idRecognize(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = fileService.storeFile(file);;
        String id_card_side = "front";
        HashMap<String, String> options = new HashMap<String, String>();
        String realFile = fileService.getFileStorePath() + "\\" + fileName;
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
        re.put("words",idno.getString("words"));

        return ServerResponse.ofSuccess(re);
    }

}