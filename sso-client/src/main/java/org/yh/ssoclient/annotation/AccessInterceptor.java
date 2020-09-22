package org.yh.ssoclient.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.yh.ssoclient.redis.AccessPrefix;
import org.yh.ssoclient.redis.RedisService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author: yh
 * @Date: 2020/9/3
 * @Description:
 */

@Component
public class AccessInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(AccessInterceptor.class);
    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod) handler;
            AccessLimit methodAnnotation = hm.getMethodAnnotation(AccessLimit.class);
            if (methodAnnotation == null) {
                return true;   //不拦截
            }
            int seconds = methodAnnotation.seconds();
            int maxTimes = methodAnnotation.maxAccessTimes();
            boolean needLogin = methodAnnotation.needLogin();
            if(needLogin){
                failAndTell(response,"系统待开发，尚不支持登录操作");
            }
            String key=request.getRequestURL().toString();
            //带有过期时间的key_prefix,单例模式(一次创建，多次访问)
            AccessPrefix accessPrefix = AccessPrefix.getInstance(seconds);
            Integer times=redisService.get(accessPrefix,key,Integer.class);
            if(times ==null){
                //第一次访问@AccessLimit注解->key方法
                redisService.set(accessPrefix,key,1);
            }else if(times>=maxTimes){
                failAndTell(response,"超出最大访问次数限制！");
            }else{
                redisService.incr(accessPrefix,key);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("interceptor#contextPath: {}",request.getContextPath());
    }


    private void failAndTell(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream out = response.getOutputStream();
        out.write(msg.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
