package com.scienjus.smartqq.mail;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;

/**
 * Created by DT167 on 2018/2/28.
 */
public interface SendMail {
    public void sendMail(Mail mail) throws Exception;
}
