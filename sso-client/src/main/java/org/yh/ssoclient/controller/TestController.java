package org.yh.ssoclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yh.ssoclient.annotation.AccessLimit;
import org.yh.ssoclient.annotation.CurrentUser;
import org.yh.ssoclient.entity.User;
import org.yh.ssoclient.utils.CookieUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Author: yh
 * @Date: 2020/9/2
 * @Description:
 *     nginx负载均衡(分布式)如何解决单点登录问题？采用ip_hash绑定用户和特定服务器，但是
 *           可能存在特定服务器压力大的问题。
 *     使用cookie解决跨域 域名配置在hosts文件 测试Url   cn.www.test.com:8080/test
 *                                                      uk.www.test.com:8080/test
 *    JWT(json web token)：
 */

@RestController
public class TestController {

    /**
     * http头部添加cookie信息
     */
    @GetMapping("/test")
    public String test(HttpServletRequest req, HttpServletResponse resp){
       String cookieName="contain_session_id";
       String charset="utf-8";
       //获取cookie信息
       String cookieValue= CookieUtil.getCookieValue(req,cookieName,charset);
       if(cookieValue==null ||"".equals(cookieValue.trim())){
           System.out.println("无cookieValue!");
           cookieValue= UUID.randomUUID().toString();
       }
       //设置cookie
        CookieUtil.setCookie(req,resp,cookieName,cookieValue,charset);
        return "test";
    }

    //自定义注解@AccessLimit实现接口访问限制
    @AccessLimit(seconds = 60,maxAccessTimes = 2)
    @GetMapping("/limit")
    String limitAccess(){
        return "limit";
    }

    //配置了HandlerArgumentResolver处理参数注解@CurrentUser
    @PostMapping("/login")
    String login(@CurrentUser User u){
        System.out.println(u);
        return "login";
    }

    @GetMapping("/exception")
    public void setException() {
        int i=1/0;
    }
}
