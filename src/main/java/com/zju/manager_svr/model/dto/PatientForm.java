package com.zju.manager_svr.model.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PatientForm {

    @NotNull(message = "patientID不能为空")
    @JsonProperty("patientID")
    private Integer patientId;
}
