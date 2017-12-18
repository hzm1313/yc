package com.cn.yc.bean;

/**
 * Created by hasee on 2017/12/13.
 */
public class ResponseBase {
    private String resultMessage;

    private int resultCode;

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}
