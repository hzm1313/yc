package smartqq.mail;

/**
 * Created by DT167 on 2018/2/28.
 */
public abstract class MailMediator {
    //定义同事类
    protected Mail mail;
    protected SendMail sendMail;

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public SendMail getSendMail() {
        return sendMail;
    }

    public void setSendMail(SendMail sendMail) {
        this.sendMail = sendMail;
    }

    //中介者模式的业务逻辑
    public abstract void sendMail() throws Exception;
}
