package com.zju.manager_svr.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.zju.manager_svr.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

public class UsernameValidator implements ConstraintValidator<UsernameValid, String> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userService.isUserExist(value);
    }

}
