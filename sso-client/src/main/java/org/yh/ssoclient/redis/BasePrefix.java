package org.yh.ssoclient.redis;

/**
 * @Author: yh
 * @Date: 2020/9/3
 * @Description:  关于key的重要属性
 */


public class BasePrefix {
    private String prefix;
    private int expire;

    public BasePrefix(String prefix) {
        this.prefix = prefix;
        this.expire=0;  //定义0永不过期
    }

    public BasePrefix(String prefix, int expire) {
        this.prefix = prefix;
        this.expire = expire;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }
}
