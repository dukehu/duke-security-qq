package com.duke.utils.sms;

/**
 * Created duke on 2018/1/7
 */
public interface SmsCodeSender {
    void send(String mobile, String code);
}
