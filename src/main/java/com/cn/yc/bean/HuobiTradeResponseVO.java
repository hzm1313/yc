package com.cn.yc.bean;

import java.util.List;

/**
 * Created by hasee on 2018/4/7.
 */
public class HuobiTradeResponseVO {
    private String ch;
    private List<HuobiTradeDetailVO> data;
    //第一次订阅返回以下字段
    private String id;
    private String status;
    private String subbed;
    private String ts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubbed() {
        return subbed;
    }

    public void setSubbed(String subbed) {
        this.subbed = subbed;
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public List<HuobiTradeDetailVO> getData() {
        return data;
    }

    public void setData(List<HuobiTradeDetailVO> data) {
        this.data = data;
    }
}
