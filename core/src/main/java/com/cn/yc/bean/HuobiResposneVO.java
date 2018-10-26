package com.cn.yc.bean;

import java.util.List;

/**
 * Created by hasee on 2018/3/22.
 */
public class HuobiResposneVO {
    private String id;
    private String rep;
    private String status;
    private List<HuobiDetailVO> data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<HuobiDetailVO> getData() {
        return data;
    }

    public void setData(List<HuobiDetailVO> data) {
        this.data = data;
    }
}
