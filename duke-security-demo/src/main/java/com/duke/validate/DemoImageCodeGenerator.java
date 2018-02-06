package com.duke.validate;

import com.duke.domain.ImageCode;
import com.duke.utils.ValidateCodeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created duke on 2018/1/6
 */
// @Component(value = "imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {

    private static final Logger logger = LoggerFactory.getLogger(DemoImageCodeGenerator.class);
    @Override
    public ImageCode generate(ServletWebRequest request) {
        logger.info(request.getRemoteUser());
        return null;
    }
}
