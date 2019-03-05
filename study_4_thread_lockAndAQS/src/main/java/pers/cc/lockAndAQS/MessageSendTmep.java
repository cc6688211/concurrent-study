package pers.cc.lockAndAQS;

import java.util.Date;

/**
 * 使用模版方法模式写一个消息发送
 * 
 * @author cc
 *
 */
public abstract class MessageSendTmep {
    /**
     * 确定发送方
     */
    public abstract void from();

    /**
     * 确定接收方
     */
    public abstract void to();

    /**
     * 确定发送内容
     */
    public abstract void content();

    /**
     * 确定发送时间
     */
    public void date() {
        System.out.println(new Date());
    }

    /**
     * 确定发送方式
     */
    public abstract void send();

    /**
     * 框架方法，即将子方法的实现交给子类去实现，定义出方法组合
     */
    public void messageSend() {
        from();
        to();
        content();
        date();
        send();
    }
}
