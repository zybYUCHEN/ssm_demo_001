package com.itcast.controller;

import com.google.code.kaptcha.Constants;
import com.itcast.domain.LoginInfo;
import com.itcast.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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


    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    public String login(@RequestParam(defaultValue = "") String username, @RequestParam(defaultValue = "") String password, HttpServletRequest request, HttpSession session) throws Exception {
        LoginInfo loginInfo = new LoginInfo();
        if (!checkValidateCode(request)) {
            loginInfo.setMsg("验证码错误");
            session.setAttribute("loginInfo", loginInfo);
            return "redirect:login.jsp";
        }
        username = username.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        try {
            Authentication authentication = authenticationManager.authenticate(authRequest); //调用loadUserByUsername
            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆
            return "main";
        } catch (AuthenticationException ex) {
            loginInfo.setMsg("用户名或密码错误");
            session.setAttribute("loginInfo", loginInfo);
            return "redirect:login.jsp";

        }
    }

    protected boolean checkValidateCode(HttpServletRequest request) {
        String result_verifyCode = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_CONFIG_KEY)
                .toString(); // 获取存于session的验证值
        request.getSession().removeAttribute(Constants.KAPTCHA_SESSION_CONFIG_KEY);
        String user_verifyCode = request.getParameter("kaptcha");// 获取用户输入验证码
        if ( user_verifyCode==null || !result_verifyCode.equalsIgnoreCase(user_verifyCode)) {
            return false;
        }
        return true;
    }
}
