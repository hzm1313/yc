package com.scienjus.smartqq.mail;

/**
 * Created by DT167 on 2018/2/28.
 */
public class YcTemplate implements Cloneable{
    private  String YcSubject="YC-black";
    private  String YcContent="";
    private  String imagePaht="";

    public String getImagePaht() {
        return imagePaht;
    }

    public void setImagePaht(String imagePaht) {
        this.imagePaht = imagePaht;
    }

    public String getYcSubject() {
        return YcSubject;
    }

    public void setYcSubject(String ycSubject) {
        YcSubject = ycSubject;
    }

    public String getYcContent() {
        return YcContent;
    }

    public void setYcContent(String ycContent) {
        YcContent = ycContent;
    }

    @Override
    public Mail clone(){
        Mail mail =null;
        try {
            mail = (Mail)super.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mail;
    }
}
