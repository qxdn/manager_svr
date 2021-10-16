package com.zju.manager_svr.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("登录表单")
@Data
public class LoginForm {

    @ApiModelProperty("用户名")
    @Size(max = 64, message = "用户名最长为64")
    @NotBlank(message = "用户名不能为空")
    private String username;
    @ApiModelProperty("密码")
    @Size(min = 6, max = 16, message = "密码最长为16，最短为6")
    @NotBlank(message = "密码不能为空")
    private String password;
}
