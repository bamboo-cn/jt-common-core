package com.bamboo.common.constant;

import com.bamboo.common.exception.Error;
/**
 * Created by lifh on 16/12/14.
 */
public enum Code {
    //code 以端口后两位打头
    ecp_not_enough_error("1001", "云算力不足"),
    admin_login_error("1002", "账号或密码错误"),
    admin_email_error("2003", "没有可用的邮箱"),
    admin_email_send_error("1004", "邮件发送失败");

    private String code;
    private String msg;

    private Code(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String code() {
        return code;
    }

    public String msg() {
        return msg;
    }

   /* public MultiResponse multiResponse() {
        return MultiResponse.builder()
                .add("code", this.code)
                .add("msg", this.msg);
    }*/

    public Error error() {
        return new Error(this.code(), this.msg());
    }
}
