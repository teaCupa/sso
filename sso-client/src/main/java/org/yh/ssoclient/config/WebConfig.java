package org.yh.ssoclient.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.yh.ssoclient.annotation.AccessInterceptor;
import org.yh.ssoclient.annotation.UserArgumentResolver;

import java.util.List;

/**
 * @Author: yh
 * @Date: 2020/9/3
 * @Description:
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    AccessInterceptor accessInterceptor;
    @Autowired
    UserArgumentResolver userArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessInterceptor);
    }

}
