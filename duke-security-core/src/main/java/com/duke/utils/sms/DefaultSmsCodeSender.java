package com.duke.utils.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created duke on 2018/1/7
 */
public class DefaultSmsCodeSender implements SmsCodeSender {

    private static final Logger logger = LoggerFactory.getLogger(DefaultSmsCodeSender.class);

    @Override
    public void send(String mobile, String code) {
        logger.info("向手机(" + mobile + ")发送验证码(" + code + ")...");
    }
}
