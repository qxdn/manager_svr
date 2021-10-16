package com.zju.manager_svr.model.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("根据id病人信息")
@Data
public class PatientForm {

    @ApiModelProperty("病人id")
    @NotNull(message = "patientID不能为空")
    @JsonProperty("patientID")
    private Integer patientId;
}
