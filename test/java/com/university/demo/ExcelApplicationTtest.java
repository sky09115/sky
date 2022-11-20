package com.university.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.university.demo.service.UserService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ModelFApplication.class)
public class ExcelApplicationTtest {

    @Autowired
    private UserService userService;

    @Test
    public void testReadAndUpdate()  {
        File f=new File("src/test/resources/用户名单 (5).xlsx");
        List<Object> datas = ExcelUtil.readExcel(fileToMultipartFile(f), new UserXls());
        //TODO 增加一个返回信息

        for(Object obj: datas){
            System.out.println(obj);

            User user = new User();
            BeanUtils.copyProperties(obj, user);  //复制到User对象里
            if(user.getId() == null || StringUtils.isEmpty(user.getUserId())){
                continue;
            }
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", user.getUserId());
            User exist = userService.getOne(wrapper);
            if(exist==null){
                user.setUserType(1);
                user.setPassword("123456");
                userService.save(user);
            }else{
                user.setId(exist.getId());  //上传的ID可能不对，所以重新设置下
//                System.out.println("user:"  + user);
                userService.updateById(user); //利用mybatis-plus去更新，不会更新掉空字段
            }
        }

    }

    public MultipartFile fileToMultipartFile(File file) {
        FileItem fileItem = createFileItem(file);
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        return multipartFile;
    }

    private static FileItem createFileItem(File file) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item = factory.createItem("textField", "text/plain", true, file.getName());
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try {
            FileInputStream fis = new FileInputStream(file);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }
}
