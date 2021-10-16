package com.zju.manager_svr.model.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("更新用户信息")
@Data
public class UpdateInfoForm {
    private String info;
    @NotNull(message = "patientID不能为空")
    @JsonProperty("patientID")
    private Integer patientId;
    private String result;
    private String state;
}
