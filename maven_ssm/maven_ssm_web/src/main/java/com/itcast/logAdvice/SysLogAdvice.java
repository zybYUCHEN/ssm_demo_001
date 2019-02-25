package com.itcast.logAdvice;

import com.itcast.controller.SysLogController;
import com.itcast.domain.SysLog;
import com.itcast.service.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @Auther : 32725
 * @Date: 2019/2/24 13:33
 * @Description: 记录系统操作的日志通知类
 * *     private String id; //主键 无意义uuid
 * *     private Date visitTime;//访问时间
 * *     private String visitTimeStr;//访问时间的字符串表示
 * *     private String username;    //访问者用户名
 * *     private String ip;  //访问者ip
 * *     private String url; //访问的资源路径
 * *     private Long executionTime; //执行时长
 * *     private String method;  //访问的方法
 */
@Component
@Aspect
public class SysLogAdvice {

    @Autowired
    private SysLogService sysLogService;

    private Date visitTime; //初次访问的时间
    private Class clazz; //访问的类
    private Method method;//访问的方法
    private String methodName;//访问的方法名
    private String url; //访问的路径
    private String IP; //访问的IP地址
    private String username; //执行访问的用户名
    private Long executionTime; //执行时长

    /**
     * @Author: 32725
     * @Param: []
     * @Return: void
     * @Description: 配置切入点表达式
     **/
    @Pointcut("execution(* com.itcast.controller.*.*(..))")
    public void pt1() {
    }

    /**
     * @Author: 32725
     * @Param: []
     * @Return: void
     * @Description: 配置前置通知
     **/
    @Before("pt1()")
    public void beforeAdvice(JoinPoint joinPoint) throws Exception {
        //1.获取访问开始的时间
        visitTime = new Date();

        //2.获取访问的类
        clazz = joinPoint.getTarget().getClass();

        //3.获取访问的方法
        //3.1 获取访问的方法名
        methodName = joinPoint.getSignature().getName();
        //3.2获取方法的参数
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            method = clazz.getMethod(methodName);
        } else {
            Class[] classArgs = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                // Model，HttpServletResponse，HttpServletRequest，HttpSession，
                // 这些类型的参数的class类型，不能正常赋予，需要手动赋值，
                // 它们会被spring框架干扰，导致类型发生变化
                // 貌似会被security的过滤器链干扰
                if (args[i] instanceof Model) {
                    classArgs[i] = Model.class;
                } else if (args[i] instanceof HttpServletResponse) {
                    classArgs[i] = HttpServletResponse.class;
                } else if (args[i] instanceof HttpServletRequest) {//StandardSessionFacade
                    classArgs[i] = HttpServletRequest.class;
                } else if (args[i] instanceof HttpSession) {
                    classArgs[i] = HttpSession.class;
                } else {
                    classArgs[i] = args[i].getClass();
                }
            }
            method = clazz.getMethod(methodName, classArgs);
        }


        //4.获取访问路径，实际上就是类上和方法上RequestMapping的值得组合
        //防止空指针异常，通知操作无需记录
        if (clazz != null && method != null && clazz != SysLogController.class) {
            //4.1 获取类上的RequestMapping注解的值
            RequestMapping clazzAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            String clazzURL = clazzAnnotation.value()[0];
            //4.2 获取方法上的RequestMapping注解的值
            RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
            String methodURL = methodAnnotation.value()[0];
            url = clazzURL + methodURL;
        }

        //5.获取访问者ip
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        IP = details.getRemoteAddress();

        //6.获取访问者用户名
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        username = userDetails.getUsername();
    }

    /**
     * @Author: 32725
     * @Param: []
     * @Return: void
     * @Description: 配置最终通知
     **/
    @After("pt1()")
    public void afterAdvice() {
        SysLog sysLog = new SysLog();//用于封装日志信息
        //7.获取执行时间
        executionTime = (new Date().getTime()) - visitTime.getTime();
        //排除日志相关操作，不记录
        if (url == null || url.equals("")) {
            return;
        }
        //8.封装数据
        sysLog.setVisitTime(visitTime);
        sysLog.setIp(IP);
        sysLog.setExecutionTime(executionTime);
        sysLog.setMethod(methodName);
        sysLog.setUrl(url);
        sysLog.setUsername(username);
        //9.执行保存操作
        sysLogService.save(sysLog);
    }
}
