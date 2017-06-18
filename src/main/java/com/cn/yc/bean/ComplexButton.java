package com.cn.yc.bean;

/**
 * Created by DT167 on 2017/6/2.
 */
public class ComplexButton{




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Button[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(Button[] sub_button) {
        this.sub_button = sub_button;
    }

    private Button[] sub_button;

    private String name;

    //private String type;

    //private String key;

    //private String url;
}
