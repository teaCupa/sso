package org.yh.ssoclient.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @Author: yh
 * @Date: 2020/9/2
 * @Description:
 */


public class CookieUtil {

    public static String getCookieValue(HttpServletRequest req, String cookieName, String charset) {
        Cookie[] cookies = req.getCookies();
        if(StringUtils.isEmpty(cookieName) || cookies==null){
            return null;
        }
        try {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals(cookieName)) {
                    return URLDecoder.decode(cookies[i].getValue(), charset);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setCookie(HttpServletRequest req, HttpServletResponse resp, String cookieName, String cookieValue,String charset) {
        try {
            cookieValue= URLEncoder.encode(cookieValue,charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Cookie cookie = new Cookie(cookieName, cookieValue);
        if(req !=null){  //设置域名
            String domain=getDomain(req);
            System.out.println(domain);
            if(! "localhost".equals(domain)){
               cookie.setDomain(domain);
            }
        }
        cookie.setMaxAge(30);  // 30s ,最大存活时间
        cookie.setPath("/");
        cookie.setHttpOnly(true); // 防止 js脚本等获取到cookie 进行xss攻击
        //cookie.setSecure(true); // 可以防止窃取cookie 只能在https传输中进行传递cookie
        resp.addCookie(cookie);
    }

    private static String getDomain(HttpServletRequest req) {
        String domain=null;
        //请求Url
        String serverUrl=req.getRequestURL().toString();
        serverUrl=serverUrl.toLowerCase();  //url不区分大小写，toLowerCase()方便字符串处理
        if(serverUrl.startsWith("http://")){
            serverUrl=serverUrl.substring(7);
        }else if(serverUrl.startsWith("https://")){
            serverUrl=serverUrl.substring(8);
        }
        final int end=serverUrl.indexOf("/");
        if(end==-1){
            serverUrl=serverUrl.substring(0);
        }else{
            serverUrl=serverUrl.substring(0,end);
        }
        String[] split = serverUrl.split("\\.");
        int len=split.length;
        if(len>=3){
            // test.demo.cn->test.demo.cn
            domain=split[len-3]+"."+split[len-2]+"."+split[len-1];
        }else if(len==2){
            domain=split[len-2]+"."+split[len-1];
        }else{
            domain=serverUrl;
        }
        //去掉端口
        if(!StringUtils.isEmpty(domain) && domain.contains(":")){
            String[] split1 = domain.split(":");
            domain=split1[0];
        }
        return domain;
    }

    public static void removeCookie(HttpServletResponse resp,String cookieName) {
        Cookie cookie = new Cookie(cookieName, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
    }
}
