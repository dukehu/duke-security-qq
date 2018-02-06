package com.duke.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created duke on 2018/1/3
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
