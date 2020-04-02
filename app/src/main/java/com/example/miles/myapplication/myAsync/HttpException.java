package com.example.miles.myapplication.myAsync;

/**
 * 用途：HttpException请求异常类
 * 作者：Created by Miles Wang on 2018/12/26
 * 邮箱：icy-star@qq.com
 **/
public class HttpException extends Exception {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4010634120321127684L;

    public HttpException() {
        super();
    }

    public HttpException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public HttpException(String detailMessage) {
        super(detailMessage);
    }

    public HttpException(Throwable throwable) {
        super(throwable);
    }
}
