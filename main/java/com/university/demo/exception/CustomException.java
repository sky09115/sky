package com.university.demo.exception;

public class CustomException extends RuntimeException {

    //可以用来接受我们方法中传的参数
    private String code;
    private String msg;

    public CustomException(String code,String msg) {
        //super("Token异常,请重新登录");
        super(msg);
        this.code = code;
        this.msg=msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}