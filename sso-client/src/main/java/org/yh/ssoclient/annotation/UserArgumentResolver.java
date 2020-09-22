package org.yh.ssoclient.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.yh.ssoclient.entity.User;

/**
 * @Author: yh
 * @Date: 2020/9/3
 * @Description:
 *     /=============================/
 *     InvocableHandlerMethod.getMethodArgumentValues()处理
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    private static final Logger log = LoggerFactory.getLogger(UserArgumentResolver.class);
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("resolver# {}",parameter);
        if(parameter.getParameterType().isAssignableFrom(User.class) &&parameter.hasParameterAnnotation(CurrentUser.class)){
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        CurrentUser currentUser = parameter.getParameterAnnotation(CurrentUser.class);
        //从session获取用户
        Object object=webRequest.getAttribute(currentUser.value(),NativeWebRequest.SCOPE_SESSION);
        if(object ==null){
            String token=webRequest.getHeader("Authorization");
            if(token==null){
//                token=webRequest.getParameter("accessToken");
            }
            //为了测试，写死用户
            return new User("zhangshan","123");
        }
        return object;
    }
}
