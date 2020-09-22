package com.example.ssojwt.entity;

/**
 * @Author: yh
 * @Date: 2020/9/20
 * @Description:
 */


public class ResponseEntity<T> {
    private Integer code;
    private String msg;
    private T object;

    public ResponseEntity(Integer code,String msg){
        this(code,msg,null);
    }

    public ResponseEntity(Integer code, String msg, T object) {
        this.code = code;
        this.msg = msg;
        this.object = object;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
