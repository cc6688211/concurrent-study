package pers.cc.lockAndAQS;

/**
 * 实现发送短险
 * 
 * @author cc
 *
 */
public class SendSms extends MessageSendTmep {

    @Override
    public void from() {
        System.out.println("18888888888");

    }

    @Override
    public void to() {
        System.out.println("16666666666");

    }

    @Override
    public void content() {
        System.out.println("hello world");

    }

    @Override
    public void send() {
        System.out.println("电信发送");

    }

    public static void main(String[] args) {
        MessageSendTmep mt = new SendSms();
        mt.messageSend();
    }
}
