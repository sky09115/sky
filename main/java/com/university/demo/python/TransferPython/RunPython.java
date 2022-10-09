package com.university.demo.python.TransferPython;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
@Slf4j
public class RunPython {

    // python命令
    @Value("${config.python-order}")
    private  String pythonOrder;
    // 调用python脚本后返回字符集名
    @Value("${config.charset-name}")
    private  String charsetName = "utf-8";

    //运行python代码,传递一个参数
    public  String runPython(String contents, String path) {
        log.info("运行python脚本, 当前python脚本路径为：{}", path);
        String[] arguments = new String[]{pythonOrder, path, contents};
        return this.transfer(arguments);
    }

    //运行python代码,传递两个参数
    public  String runPython(String contents01, String contents02, String path) {
        log.info("运行python脚本，传入的两个参数分别为contents01:{}, contents02:{}", contents01, contents02);
        log.info("当前python脚本路径为：{}", path);
        String[] arguments = new String[]{pythonOrder, path, contents01, contents02};
        return this.transfer(arguments);

    }

    /**
     * 调用python脚本
     * @param arguments
     * @return
     */
    public String transfer(String[] arguments) {
        String content = "";
        try {
            Process process = Runtime.getRuntime().exec(arguments);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), charsetName));
            String line;
            while ((line = in.readLine()) != null) {
                content = line;
            }
            in.close();
            int re = process.waitFor();
            log.info("python脚本返回状态码：{}", re);
            System.out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }
}