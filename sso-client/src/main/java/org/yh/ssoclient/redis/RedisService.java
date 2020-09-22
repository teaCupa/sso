package org.yh.ssoclient.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import static org.yh.ssoclient.utils.TypeTransfer.beanToString;
import static org.yh.ssoclient.utils.TypeTransfer.stringToBean;

/**
 * @Author: yh
 * @Date: 2020/9/3
 * @Description:
 */

@Service
public class RedisService {
    @Autowired
    JedisPool jedisPool;

    public <T>Boolean set(AccessPrefix accessPrefix, String key, T value) {
        Jedis jedis = jedisPool.getResource();
        String realKey=accessPrefix+"_"+key;
        String str=beanToString(value);
        if(str==null ||"".equals(str)){
            return false;
        }
        int seconds=accessPrefix.getExpire();
        if(seconds==0){
            //永不过期
          jedis.set(realKey,str);
        }else{
          jedis.setex(realKey,seconds,str);  //设置key，过期时间，value
        }
        returnToPool(jedis);
        return true;
    }

    public <T>T get(AccessPrefix accessPrefix, String key, Class<T> clazz) {
        Jedis jedis = jedisPool.getResource();
        String realKey=accessPrefix+"_"+key;
        String str = jedis.get(realKey);
        T t= stringToBean(str,clazz);
        returnToPool(jedis);
        return t;
    }

    private void returnToPool(Jedis jedis) {
        if(jedis !=null){
            jedis.close();
        }
    }

    public Long incr(AccessPrefix accessPrefix, String key) {
        Jedis jedis = jedisPool.getResource();
        String realKey=accessPrefix+"_"+key;
        Long res = jedis.incr(realKey);
        returnToPool(jedis);
        return res;
    }
}
