package com.bamboo.common.http.response;

import java.util.HashMap;
import java.util.Map;
import com.bamboo.common.exception.Error;

public class MultiResponse2 extends HashMap<String, Object> {
    private Map<Object, Object> data = new HashMap();

    private MultiResponse2() {
    }

    public static MultiResponse2 builder() {
        return new MultiResponse2();
    }

    public MultiResponse2 addData(String key, Object value) {
        this.data.put(key, value);
        this.putIfAbsent("data", this.data);
        return this;
    }

    public MultiResponse2 add(String key, Object value) {
        //this.addData(key, value);
        this.put("data", value);
        return this;
    }

    public MultiResponse2 success() {
        return this.success("成功");
    }

    public MultiResponse2 success(String msg) {
        this.put("code", "0000");
        this.put("msg", msg == null ? "成功" : msg);
        this.put("data", this.data);
        return this;
    }

    public MultiResponse2 error() {
        this.put("code", "9999");
        this.put("msg", "系统好像迷路了");
        return this;
    }

    public MultiResponse2 error(Error error) {
        this.put("code", error.getCode());
        this.put("msg", error.getMsg());
        return this;
    }
}