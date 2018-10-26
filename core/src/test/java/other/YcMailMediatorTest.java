package other;

import smartqq.mail.*;
import org.junit.Test;

/**
 * Created by DT167 on 2018/2/28.
 */
public class YcMailMediatorTest {
    @Test
    public void sendMailTest(){
        YcTemplate ycTemplate = new YcTemplate();
        ycTemplate.setImagePaht("C:\\Users\\Public\\Pictures\\Sample Pictures\\0.jpg");
       /* ycTemplate.setYcContent("aaaa");
        ycTemplate.setYcSubject("bbbb");*/
        Mail mail = new Mail(ycTemplate);
        SendMail sendMail = new YcSendMail();
        YcMailMediator yc = new YcMailMediator(mail,sendMail);
        try {
            yc.sendMail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
