package com.zju.manager_svr.handler;

import javax.servlet.http.HttpServletRequest;

import com.zju.manager_svr.model.dto.ReturnBean;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ValidatorHandler {

    /**
     * 处理验证失败
     * 
     * @param exception
     * @return
     */
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ReturnBean validHandler(HttpServletRequest request, MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        // 失败表单
        Object instance = bindingResult.getTarget();
        // 失败原因
        String msg = bindingResult.getFieldError().getDefaultMessage();
        StringBuffer sb = new StringBuffer();
        sb.append(request.getRequestURI());
        sb.append(":出错");
        log.error(msg, instance);
        return ReturnBean.failReturn(msg, sb.toString());
    }
}
