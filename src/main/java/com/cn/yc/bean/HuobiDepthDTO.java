package com.cn.yc.bean;

/**
 * Created by hasee on 2018/4/7.
 */
public class HuobiDepthDTO {
    private String sub;
    private String id;
    //订阅成功第一次返回以下字段
    private String subbed;
    private String ts;

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
