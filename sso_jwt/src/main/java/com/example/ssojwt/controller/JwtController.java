package com.example.ssojwt.controller;

import com.example.ssojwt.entity.ResponseEntity;
import com.example.ssojwt.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: yh
 * @Date: 2020/9/20
 * @Description:   登录成功->生成Token->前端将其存储在localStorage
 */

@Controller
public class JwtController {
    @PostMapping("/doLogin")
    @ResponseBody  //这个注解作用：返回restful 而不是页面
    public ResponseEntity validLogin(@RequestParam("name")String name,@RequestParam("password") String password) throws IOException {
        if("zhangsan".equals(name) && "123".equals(password)){
            User u = new User(name, password);
            String jwt = Jwts.builder()
                    .claim("user", u)  //key-value
                    .setId(UUID.randomUUID().toString())
                    .setSubject(name)
                    .setExpiration(new Date(System.currentTimeMillis() + 1 * 60 * 1000)) //1 min
                    .signWith(SignatureAlgorithm.HS512, "yh@123")  //算法，密匙
                    .compact();  //生成token
            return new ResponseEntity(200,"登录成功",jwt);
        } else{
            return new ResponseEntity(400,"登录失败");
        }
    }

    @GetMapping("/")
    String login(){
        return "login";
    }


}
