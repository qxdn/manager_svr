package com.zju.manager_svr.model.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("添加病人")
@Data
public class AddPatientForm {

    @ApiModelProperty("病人名")
    @NotBlank(message = "病人名不能为空")
    @JsonProperty("name")
    private String username;

    @ApiModelProperty("年龄")
    @Min(value = 0, message = "年龄不能小于0")
    private Integer age;

    @ApiModelProperty("0为男 1为女")
    @Min(value = 0, message = "性别只能为0或1")
    @Max(value = 1, message = "性别只能为0或1")
    private Integer sex;

    @ApiModelProperty("病例ID")
    @NotBlank(message = "病例ID不能为空")
    @JsonProperty("recordID")
    private String recordId;

    @ApiModelProperty("病人基础信息")
    private String info;

    @ApiModelProperty("检查结果")
    private String result;

    @ApiModelProperty("病人状态")
    private String state;

}
