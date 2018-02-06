package com.duke.utils.sms;

import com.duke.domain.ValidateCode;
import com.duke.properties.SecurityProperties;
import com.duke.utils.ValidateCodeGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created duke on 2018/1/7
 */
@Component(value = "smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String smsCode = RandomStringUtils.randomNumeric(securityProperties.getCode().getSmsCode().getLength());

        return new ValidateCode(smsCode, securityProperties.getCode().getSmsCode().getExpireInSeconds());
    }
}
