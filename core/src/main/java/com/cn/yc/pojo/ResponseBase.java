package com.cn.yc.pojo;

/**
 * Created by hasee on 2017/9/3.
 */
public class ResponseBase {
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String status;
    private String content;
}
