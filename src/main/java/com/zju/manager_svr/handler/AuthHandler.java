package com.zju.manager_svr.handler;

import com.zju.manager_svr.exception.NotLoginException;
import com.zju.manager_svr.model.dto.ReturnBean;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class AuthHandler {
    @ExceptionHandler({ NotLoginException.class })
    public ReturnBean loginHandler(NotLoginException exception) {
        log.error("login require:未登录请先登陆");
        return ReturnBean.failReturn("", "未登录请先登陆");
    }
}
