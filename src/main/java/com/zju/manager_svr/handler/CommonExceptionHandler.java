package com.zju.manager_svr.handler;

import javax.servlet.http.HttpServletRequest;

import com.zju.manager_svr.exception.DeleteUploadException;
import com.zju.manager_svr.model.dto.ReturnBean;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler({ DeleteUploadException.class })
    public ReturnBean loginHandler(DeleteUploadException exception) {
        log.error("删除病人失败", exception);
        return ReturnBean.failReturn("", "delPatient出错");
    }

    @ExceptionHandler({ Exception.class })
    public ReturnBean commonHandler(Exception exception, HttpServletRequest request) {
        StringBuffer msg = new StringBuffer();
        msg.append(request.getRequestURI());
        msg.append(":出错");
        log.error(msg.toString(), exception);
        return ReturnBean.failReturn("", msg.toString());
    }
}
