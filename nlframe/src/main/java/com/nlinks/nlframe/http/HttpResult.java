package com.nlinks.nlframe.http;

import java.io.Serializable;

/**
 * 网络请求通用基本对象
 */
public class HttpResult<T> implements Serializable {

    // 状态 0：失败 1：成功
    private int status;

    // 结果的编码（每个服务自己定义）
    private int code;

    // 返回的内容
    private T content;

    // 返回的消息
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
