sso-client工程使用了springboot的一些技术
包括自定义参数级注解String login(@CurrentUser User u)  通过HandlerMethodArgumentResolver中
    的resolveArgument(MethodParameter parameter,...) 方法获取注解
        CurrentUser currentUser = parameter.getParameterAnnotation(CurrentUser.class);
定义HandlerInterceptorAdapter拦截器实现 @AccessLimit(seconds = 60,maxAccessTimes = 2)限流功能

以上功能需要在WebMvcConfigurer容器中注册
@Configuration
public class WebConfig implements WebMvcConfigurer {}

定义配置信息 
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {}
定义了JedisPoolFactory 组件，在其中注入了JedisPool这一Bean对象
Jedis jedis = jedisPool.getResource();

使用cookie解决跨域 域名配置在hosts文件 测试Url   cn.www.test.com:8080/test
                                                       uk.www.test.com:8080/test  这些url请求必须是同一个会话（多个同源请求）
本质上是在服务端在请求头中写入cookie,由客户端保存，服务端通过session保存用户信息，运行用户跨页面访问（用户信息仍在）

sso-jwt 工程:
      理解了@Controller注解配合@ResponseBody返回的不是页面，而是restful字符串
     生产Token核心代码：
              String jwt = Jwts.builder()
                    .claim("user", u)  //key - value
                    .setId(UUID.randomUUID().toString())
                    .setSubject(name)
                    .setExpiration(new Date(System.currentTimeMillis() + 1 * 60 * 1000)) //1 min
                    .signWith(SignatureAlgorithm.HS512, "yh@123")  //算法，密匙
                    .compact();  //生成token
     解析Token:
               claims = Jwts.parser().setSigningKey("yh@123")
                        .parseClaimsJws(jwtToken).getBody();
       获取信息 claims.getSubject(); 以及根据key获取value : User u=claims.get("user")
   可以考虑将token存放在redis中 redisTemplate.get("key") 在登录成功后存入token，校验时获取token
   这里是用ajax的beforeSend函数，xhr.setRequestHeader('key',token)来存放token到请求头中

	

     