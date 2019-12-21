package com.bamboo.common.constant;

public enum BussType {
    //code 以端口后两位打头
    buss_factory("1001", "factory"),
    buss_product("1002", "product"),
    admin_login_error("1002", "账号或密码错误");

    private String code;
    private String type;

    private BussType(String code, String type) {
        this.code = code;
        this.type = type;
    }

    public String code() {
        return code;
    }

    public String type() {
        return type;
    }

}