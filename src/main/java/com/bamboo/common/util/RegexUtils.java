package com.bamboo.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,20}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,20}$";

    /**
     *正则表达式：复杂密码校验
     * 数字,字母,特殊字符(不包括空格表情符) 至少两种组合6-16位密码
     */
    public static final String REGEX_PASSWORD_COMPLEX = "(((?=.*\\d)(?=.*[a-zA-Z]))|((?=.*\\d)(?=.*[`~!@#$%^&*()={}':;,.<>\\/?\\-_+\\|\\\\\\[\\]\"\\/]))|((?=.*[a-zA-Z])(?=.*[`~!@#$%^&*()={}':;,.<>\\/?\\-_+\\|\\\\\\[\\]\"\\/])))[\\dA-Za-z`~!@#$%^&*()={}':;,.<>\\/?\\-_+\\|\\\\\\[\\]\"\\/]{6,16}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    /**
     * 正则表达式：验证银行卡号
     */
    public static final String REGEX_BANK_NO = "^([1-9]{1})(\\d{14,18})$";

    /*********
     * 正则匹配检查
     * @param regex 正则表达式
     * @param input 被检测的字符串
     * @return  匹配则返回trur,不匹配为false
     * @author zjcjava@163.com
     * @time 2017-6-10
     */
    public static boolean matcher(String regex,CharSequence input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);// 判断是否是匹配
        boolean result = matcher.matches();
        return result;
    }


    public static void main(String args []) {
        System.out.println(matcher("^[a-zA-Z,\u4e00-\u9fa5]{2,30}$", "中国该1"));
    }
}