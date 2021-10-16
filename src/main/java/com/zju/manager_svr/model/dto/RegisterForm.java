package com.zju.manager_svr.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.zju.manager_svr.valid.UsernameValid;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("注册表单")
@Data
public class RegisterForm {

    @ApiModelProperty("用户名")
    @Size(max = 64, message = "用户名最长为64")
    @NotBlank(message = "用户名不能为空")
    @UsernameValid(message = "用户名已存在")
    private String username;

    @ApiModelProperty("真实姓名")
    @Size(max = 64, message = "真实姓名最长为64")
    @NotBlank(message = "真实姓名不能为空")
    private String realname;

    @ApiModelProperty("密码")
    @Size(min = 1, max = 64, message = "密码要为1到64位")
    @NotBlank(message = "密码不能为空")
    private String password;
}
