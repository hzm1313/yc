package com.cn.yc.bean;

/**
 * Created by hasee on 2018/3/18.
 */
public class HuobiKlineReqDTO {
    private String req;
    private String id;
    private long from;
    private long to;

    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }
}
