package com.itcast.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * @author allen
 * @description 因为从 servlet 2.3 版本开始，JSP 只能接受 GET、POST 和 HEAD 请求。该拦截器会处理所有转发目标为
 * JSP 的请求，更改请求方法为 POST。
 * @date 2018/12/29
 */
public class ForwardedHttpMethodFilter implements BeanFilter {
    private static final List<String> ALLOWED_METHODS = Collections.unmodifiableList(Arrays.asList("PUT", "DELETE", "PATCH"));

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
            throw new ServletException("HttpMethodFilter just supports HTTP requests");
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (ALLOWED_METHODS.contains(httpRequest.getMethod()) && httpRequest.getAttribute("javax.servlet.error.exception") == null) {
            httpRequest = new ForwradedHttpMethodRequestWrapper(httpRequest);
        }
        chain.doFilter(httpRequest, httpResponse);
    }

    private static class ForwradedHttpMethodRequestWrapper extends HttpServletRequestWrapper {
        public ForwradedHttpMethodRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getMethod() {
            return "POST";
        }
    }
}
