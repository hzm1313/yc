package com.scienjus.smartqq.mail;

import java.util.ArrayList;

/**
 * Created by DT167 on 2018/2/28.
 */
public class Mail {
    private String receiver;
    private String subject;
    private String application;
    private String content;
    private String tail;
    private String imagePath;

    public Mail(YcTemplate ycTemplate){
        this.subject = ycTemplate.getYcSubject();
        this.content = ycTemplate.getYcContent();
        this.imagePath = ycTemplate.getImagePaht();
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }
}
