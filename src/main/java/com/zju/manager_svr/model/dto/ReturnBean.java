package com.zju.manager_svr.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("返回信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnBean {

    @JsonIgnore
    private static final Logger log = LoggerFactory.getLogger(ReturnBean.class);

    private String status;
    private Object data;
    private String msg;

    public static ReturnBean successReturn(Object data, String msg) {
        ReturnBean success = new ReturnBean("success", data, msg);
        log.info("return success {}", success);
        return success;
    }

    public static ReturnBean failReturn(Object data, String msg) {
        ReturnBean fail = new ReturnBean("fail", data, msg);
        log.info("return fail {}", fail);
        return fail;
    }
}
