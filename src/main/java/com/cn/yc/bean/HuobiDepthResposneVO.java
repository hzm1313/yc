package com.cn.yc.bean;

/**
 * Created by hasee on 2018/4/7.
 */
public class HuobiDepthResposneVO {
    private String ch;
    private String ts;
    private HuobiDepthDetail tick;


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

    public HuobiDepthDetail getTick() {
        return tick;
    }

    public void setTick(HuobiDepthDetail tick) {
        this.tick = tick;
    }
}
