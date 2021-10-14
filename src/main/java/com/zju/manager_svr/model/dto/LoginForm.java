package com.zju.manager_svr.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginForm {

    @Size(max = 64, message = "用户名最长为64")
    @NotBlank(message = "用户名不能为空")
    private String username;
    @Size(min = 6, max = 16, message = "密码最长为16，最短为6")
    @NotBlank(message = "密码不能为空")
    private String password;
}
