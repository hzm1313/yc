package com.cn.smartqq.mail;

/**
 * Created by DT167 on 2018/2/28.
 */
public class YcMailMediator extends MailMediator {

    public YcMailMediator(Mail mail, SendMail sendMail) {
        this.mail = mail;
        this.sendMail = sendMail;
    }

    @Override
    public void sendMail() throws Exception {
        sendMail.sendMail(mail);
    }
}
