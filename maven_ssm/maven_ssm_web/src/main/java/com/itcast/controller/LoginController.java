package com.itcast.controller;

import com.google.code.kaptcha.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Auther : 32725
 * @Date: 2019/2/27 18:43
 * @Description: 自定义的认证管理器
 */
@Controller
@RequestMapping("/user")
public class LoginController {

    /**
     * 注入身份认证管理器
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String username, String password, String kaptcha, HttpSession session) throws Exception {
       //校验验证码是否正确，不正确返回登陆页面
        if (kaptcha == null || !session.getAttribute(Constants.KAPTCHA_SESSION_CONFIG_KEY).toString().equals(kaptcha)) {
            return "redirect:login.jsp";
        }
        // 创建用户名与密码认证对象
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(token);
        if (authenticate.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            // 重定向到登录成功页面
            return "main";
        }
        return "redirect:login.jsp";
    }
}
