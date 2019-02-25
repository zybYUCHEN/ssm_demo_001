package com.itcast.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Auther : 32725
 * @Date: 2019/2/25 19:32
 * @Description:
 */
public interface BeanFilter extends Filter {
    @Override
    default void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException;

    @Override
    default void destroy() {

    }
}
