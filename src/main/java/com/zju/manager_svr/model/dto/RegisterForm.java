package com.zju.manager_svr.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.zju.manager_svr.valid.UsernameValid;

import lombok.Data;

@Data
public class RegisterForm {
    @Size(max = 64, message = "用户名最长为64")
    @NotBlank(message = "用户名不能为空")
    @UsernameValid(message = "用户名已存在")
    private String username;
    @Size(max = 64, message = "真实姓名最长为64")
    @NotBlank(message = "真实姓名不能为空")
    private String realname;
    @Size(min = 1, max = 64, message = "密码要为1到64位")
    @NotBlank(message = "密码不能为空")
    private String password;
}
