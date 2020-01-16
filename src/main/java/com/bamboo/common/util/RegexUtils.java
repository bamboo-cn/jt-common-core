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
     * 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
     */
    public static final String REGEX_ID_CARD_SUPER = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
            "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";

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


    /**
     * 验证是否是身份证号
     * @param idCardNo
     * @return
     */
    public static boolean isIdCardNo(String idCardNo) {
        if (idCardNo == null || "".equals(idCardNo)) {
            return false;
        }
        boolean matches = idCardNo.matches(REGEX_ID_CARD_SUPER);
        //判断第18位校验值
        if (matches) {

            if (idCardNo.length() == 18) {
                try {
                    char[] charArray = idCardNo.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                       /* System.out.println("身份证最后一位:" + String.valueOf(idCardLast).toUpperCase() +
                                "错误,正确的应该是:" + idCardY[idCardMod].toUpperCase());*/
                        return false;
                    }

                } catch (Exception e) {
                    /* e.printStackTrace();*/
                    /* System.out.println("异常:" + idCardNo);*/
                    return false;
                }
            }

        }
        return matches;
    }

    public static void main(String args []) {
        System.out.println(matcher("^[a-zA-Z,\u4e00-\u9fa5]{2,30}$", "中国该1"));
    }
}