package com.zju.manager_svr.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zju.manager_svr.annotation.LoginRequire;
import com.zju.manager_svr.exception.NotLoginException;
import com.zju.manager_svr.util.JWTUtil;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getHeader("Authorization");// 从 http 请求头中取出 token
        // 不是映射方法就通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 检查是否有login require注释，无则跳过认证
        if (!method.isAnnotationPresent(LoginRequire.class)) {
            return true;
        }
        LoginRequire loginRequire = method.getAnnotation(LoginRequire.class);
        // 是否需要该认证
        if (loginRequire.required()) {
            // 判断token是否存在
            if (token == null) {
                throw new NotLoginException("login_required: 未登录请先登陆");
            }
            // 获取 token 中的 user id
            Integer userId = JWTUtil.identify(token);
            // 无法解码出token
            if (null == userId) {
                throw new NotLoginException("login_required: 未登录请先登陆");
            }
            // 获取session
            HttpSession session = request.getSession();
            // 存入session
            session.setAttribute("user_id", userId);
        }
        return true;
    }
}
