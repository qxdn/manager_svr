package com.zju.manager_svr.controller;

import java.util.Map;
import java.util.HashMap;

import com.auth0.jwt.interfaces.Claim;
import com.zju.manager_svr.model.dto.LoginForm;
import com.zju.manager_svr.model.dto.RegisterForm;
import com.zju.manager_svr.model.dto.ReturnBean;
import com.zju.manager_svr.model.entity.User;
import com.zju.manager_svr.service.UserService;
import com.zju.manager_svr.util.HashUtil;
import com.zju.manager_svr.util.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Slf4j
public class ApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ReturnBean register(@RequestBody @Validated RegisterForm form) {
        form.setPassword(HashUtil.encode(form.getPassword()));
        User user = new User(form.getUsername(), form.getPassword(), form.getRealname());
        if (userService.registerUser(user)) {
            return ReturnBean.successReturn(null, "register: 注册成功");
        }
        return ReturnBean.failReturn(null, "注册失败");
    }

    @PostMapping("/login")
    public ReturnBean login(@RequestBody @Validated LoginForm form) {
        User user = userService.findUserByUsername(form.getUsername());
        if (null == user || !HashUtil.verification(form.getPassword(), user.getPassword())) {
            return ReturnBean.failReturn("", "用户名或密码错误");
        }
        String accessToken = JWTUtil.generate_access_token(user.getId());
        String refreshToken = JWTUtil.generate_refresh_token(user.getId());
        Map<String, Object> data = new HashMap<>();
        data.put("access_token", accessToken);
        data.put("refresh_token", refreshToken);
        return ReturnBean.successReturn(data, "login: 登陆成功");
    }

    @PostMapping("/refreshToken")
    public ReturnBean refreshToken(@RequestBody Map<String, Object> map) {
        String token = (String) map.getOrDefault("refresh_token", "");
        if (token.isBlank()) {
            return ReturnBean.failReturn("", "refreshToken: 参数错误");
        }
        Map<String, Claim> payload = JWTUtil.decode_auth_token(token);
        if (null == payload) {
            return ReturnBean.failReturn("", "refreshToken: 请登陆");
        }
        String accessToken = JWTUtil.generate_access_token(payload.get("user_id").asInt());
        Map<String, Object> data = new HashMap<>();
        data.put("access_token", accessToken);
        return ReturnBean.successReturn(data, "refreshToken: 刷新成功");
    }

}
