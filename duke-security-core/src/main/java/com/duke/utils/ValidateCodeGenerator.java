package com.duke.utils;

import com.duke.domain.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created duke on 2018/1/5
 */
public interface ValidateCodeGenerator {


    ValidateCode generate(ServletWebRequest request);

}
