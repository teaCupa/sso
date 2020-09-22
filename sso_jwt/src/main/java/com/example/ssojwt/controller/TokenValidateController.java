package com.example.ssojwt.controller;

import com.example.ssojwt.entity.ResponseEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: yh
 * @Date: 2020/9/20
 * @Description:  校验token是否有效,token存储在application->localStorage
 */

@Controller
public class TokenValidateController {
    private static final Logger log = LoggerFactory.getLogger(TokenValidateController.class);

    @GetMapping("/valid")
    @ResponseBody
    public ResponseEntity validateToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        // 适用于Postman->Authorization->Bearer token字段加入token测试
        //String jwtToken = req.getHeader("authorization");
        //可以考虑将token存放在redis中 redisTemplate.get("key") 在登录成功后存放token
        String jwtToken = req.getHeader("Authorization"); // xhr.setRequestHeader("Authorization",token)
        System.out.println(jwtToken);
        if (!StringUtils.isEmpty(jwtToken)) {
         Claims claims = null;
            try {
                claims = Jwts.parser().setSigningKey("yh@123")
//                        .parseClaimsJws(jwtToken.replace("Bearer", "")).getBody();
                        .parseClaimsJws(jwtToken).getBody();
            } catch (ExpiredJwtException e) {
                //token过期
//                claims=e.getClaims();
                log.error("token is expired.");
                return new ResponseEntity(401, "token is expired!");
            } catch (SignatureException e) {
                log.error("校验失败");
                return new ResponseEntity(402, "校验失败");
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(407, "校验token异常：" + e.getMessage());
            }
            String username = claims.getSubject();
            System.out.println("user:  " + claims.get("user"));
            //可以返回用户关联信息
            return new ResponseEntity(200, "valid pass!",jwtToken);
        } else {
            return new ResponseEntity(304, "token is empty..");
        }
    }


}
