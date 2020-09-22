package org.yh.ssoclient.utils;

import com.alibaba.fastjson.JSON;

/**
 * @Author: yh
 * @Date: 2020/9/3
 * @Description:  类型转化
 */


public class TypeTransfer {
    public static <T>T stringToBean(String str,Class<T> clazz){
        if(str==null ||"".equals(str)){
            return null;
        }else if(clazz==Integer.class ){
            return (T) Integer.valueOf(str);
        }else if(clazz==Long.class){
            return (T) Long.valueOf(str);
        }else if(clazz ==String.class){
           return (T) str;
        }else{
            return JSON.toJavaObject(JSON.parseObject(str),clazz);
        }
    }

    public static <T>String beanToString(T value){
        if(value ==null){
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz ==Integer.class){
            return ""+value;
        }else if(clazz ==Long.class){
            return ""+value;
        }else if(clazz ==String.class){
            return (String) value;
        }else{
            return JSON.toJSONString(value);
        }
    }
}
