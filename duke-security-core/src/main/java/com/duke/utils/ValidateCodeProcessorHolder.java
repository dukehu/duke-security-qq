package com.duke.utils;

import com.duke.exception.ValidateCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created duke on 2018/1/9
 */
@Component
public class ValidateCodeProcessorHolder {

    /**
     * spring会自动将 ValidateCodeProcessor 接口的实例放在map中
     * key是实例（bean）的名称，值是哪一个bean
     */
    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
        return findValidateCodeProcessor(type.toString().toLowerCase());
    }

    public ValidateCodeProcessor findValidateCodeProcessor(String type) {
        String beanName = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
        ValidateCodeProcessor validateCodeProcessor = validateCodeProcessors.get(beanName);
        if (validateCodeProcessor == null) {
            throw new ValidateCodeException("验证码处理器" + beanName + "不存在！");
        }
        return validateCodeProcessor;
    }
}
