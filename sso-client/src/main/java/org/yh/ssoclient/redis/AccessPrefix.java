package org.yh.ssoclient.redis;

/**
 * @Author: yh
 * @Date: 2020/9/3
 * @Description:
 */


public class AccessPrefix extends BasePrefix {
   private static AccessPrefix accessPrefix;
   private static String ACCESSPREFIXNAME="ap";

    public AccessPrefix(String prefix) {
        super(prefix);
    }

    public AccessPrefix(String prefix, int expire) {
        super(prefix, expire);
    }

    public static AccessPrefix getInstance(int seconds) {
        if(accessPrefix==null){
            accessPrefix=new AccessPrefix(ACCESSPREFIXNAME,seconds);
        }
        return accessPrefix;
    }
}
