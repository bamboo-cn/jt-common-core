package com.bamboo.common.constant;

import com.bamboo.common.exception.Error;

public enum PublicCode {
    success("0000", "成功"),
    sign_error("0001", "sign错误"),
    repeat_commit_error("0002", "重复提交"),
    params_empty_error("0003", "参数为空"),
    params_input_error("0004", "参数错误"),
    phone_error("0005", "手机号码不正确"),
    phone_reg_exists_error("0006", "手机号码已经注册"),
    upload_file_error("0007", "上传文件失败"),
    get_file_error("0008", "获取文件信息失败"),
    pic_content_empty_error("0009", "图片数据不能为空"),
    grant_type_error("0010", "grant_type参数不正确"),
    token_empty_error("0011", "token为空"),
    token_expire_or_absent_error("0012", "token不存在或者过期"),
    token_type_error("0013", "token类型错误"),
    token_expire_error("0014", "token已经过期"),
    token_key_error("0015", "解析token的key错误"),
    token_check_error("0016", "校验token错误"),
    access_token_sign_error("0017", "sign错误"),
    access_token_error("0018", "access_token验证错误"),
    user_exists_error("0030", "用户已经注册"),
    factoryName_exists_error("0031","该卖家已存在"),
    productName_exists_error("0032","该产品已存在"),
    brandName_exists_error("0033","该品牌已存在"),
    traderName_exists_error("0034","该买家已存在"),
    access_token_expires_error("1000", "access_token过期或者不存在"),
    refresh_token_expires_error("1001", "refresh_token过期或者不存在"),
    access_token_check_uid_error("1002", "验证用户失败"),
    access_token_auth_limit("1003", "没有权限访问该接口"),
    config_error("9990", "获取配置信息失败"),
    system_service_stop_error("9991", "该项服务已经停止"),
    system_error("9999", "系统好像迷路了"),
    jwt_expired("5102", "令牌已过期"),
    jwt_invalid("5103", "令牌无效"),
    jwt_no_permission("5104", "令牌无权限访问此接口"),
    jwt_missing_header("5105", "缺少请求头"),
    jwt_check_error("5106", "令牌检校失败"),
    jwt_not_match_user("5106", "令牌信息不匹配");

    private String code;
    private String msg;

    private PublicCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return this.code().equals(success.code());
    }

    public static boolean isSuccess(String code) {
        return success.code().equals(code);
    }

    public String code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }

//    public MultiResponse multiResponse() {
//        return MultiResponse.builder().add("code", this.code).add("msg", this.msg);
//    }

    public Error error() {
        return new Error(this.code(), this.msg());
    }
}
