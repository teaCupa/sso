package org.yh.ssoclient.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: yh
 * @Date: 2020/9/19
 * @Description:  dispatcher->filter->resolver->interceptor->handler
 */

@Configuration
public class MyFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(MyFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        log.info("filter#请求url: {}",url);
        ((HttpServletResponse) response).addHeader("headInfo","hi");
        chain.doFilter(request,response);  //进入下一个过滤器，没有过滤器就进入DisPatcherServlet
    }

    @Override
    public void destroy() {

    }
}
