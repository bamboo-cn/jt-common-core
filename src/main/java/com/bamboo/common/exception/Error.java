package com.bamboo.common.exception;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Error implements Serializable {
    private String code;
    private Object msg;

    public Error() {
    }

    public Error(String code, Object msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getMsg() {
        return this.msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public Map<String, Object> map() {
        Map<String, Object> map = new HashMap();
        map.put("code", this.code);
        map.put("msg", this.msg);
        return map;
    }
}
