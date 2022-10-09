package com.university.demo.config.handler;

import com.university.demo.entity.system.ServerResponse;
import com.university.demo.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: 15760
 * @Date: 2020/3/17
 * @Descripe: 统一异常处理
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 对所有的异常进行相同的处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ServerResponse error(Exception e) {
        e.printStackTrace();
        LOG.error("the error message is:" + "    " + e.getMessage());
        return ServerResponse.ofError("服务器出现异常");
    }

    // 对特定异常进行处理,更改@ExceptionHandler()中异常的类型即可
    // 如@ExceptionHandler(IOException.class)
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ServerResponse customError(CustomException e) {
        e.printStackTrace();
        LOG.error("自定义错误 message is:" + "    " + e.getCode() + "," + e.getMsg());
        return ServerResponse.ofError(e.getMessage());
    }
}
