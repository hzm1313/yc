package com.cn.yc.bean;

import java.util.Date;

/**
 * Created by DT167 on 2017/6/19.
 */
public class Pray extends BaseDto{
    private int id;
    private int userId;
    private String prayObject;
    private String prayType;
    private String prayConect;
    private String prayContent;
    private String praySex;
    private String prayAge;
    private String money;
    private Date date;

    public String getPrayConect() {
        return prayConect;
    }

    public void setPrayConect(String prayConect) {
        this.prayConect = prayConect;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPrayObject() {
        return prayObject;
    }

    public void setPrayObject(String prayObject) {
        this.prayObject = prayObject;
    }

    public String getPrayType() {
        return prayType;
    }

    public void setPrayType(String prayType) {
        this.prayType = prayType;
    }

    public String getPrayContent() {
        return prayContent;
    }

    public void setPrayContent(String prayContent) {
        this.prayContent = prayContent;
    }

    public String getPraySex() {
        return praySex;
    }

    public void setPraySex(String praySex) {
        this.praySex = praySex;
    }

    public String getPrayAge() {
        return prayAge;
    }

    public void setPrayAge(String prayAge) {
        this.prayAge = prayAge;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
