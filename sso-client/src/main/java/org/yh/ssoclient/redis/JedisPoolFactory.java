package org.yh.ssoclient.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: yh
 * @Date: 2020/9/3
 * @Description:
 */

@Component
public class JedisPoolFactory {
    @Autowired
    RedisConfig redisConfig;
    @Bean
    public JedisPool jedisFactory(){
        JedisPoolConfig jpc = new JedisPoolConfig();
        jpc.setMaxIdle(redisConfig.getPoolMaxIdle());
        jpc.setMaxTotal(redisConfig.getPoolMaxIdle());
        jpc.setMaxWaitMillis(redisConfig.getPoolMaxWait()*1000);
        JedisPool jp = new JedisPool(jpc, redisConfig.getHost(), redisConfig.getPort(), redisConfig.getTimeout() * 1000
                , redisConfig.getPassword(), redisConfig.getDatabase());
        return jp;
    }
}
