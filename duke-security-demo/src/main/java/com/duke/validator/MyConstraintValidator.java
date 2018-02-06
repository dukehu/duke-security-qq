package com.duke.validator;

import com.duke.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by duke on 2017/12/25
 */
public class MyConstraintValidator implements ConstraintValidator<MyConstraint, String> {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MyConstraintValidator.class);
    @Autowired
    private UserService userService;

    @Override
    public void initialize(MyConstraint constraintAnnotation) {
        // System.out.println("my validator init");
        LOGGER.info("my validator init");
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userService.exist(value);
    }
}
