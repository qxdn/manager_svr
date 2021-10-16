package com.zju.manager_svr.model.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("refresh token表单")
@Data
public class RefreshForm {

    @ApiModelProperty("token")
    @NotBlank(message = "token不能为空")
    @JsonProperty("refresh_token")
    private String refreshToken;
}
