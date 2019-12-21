package com.bamboo.common.constant;

/**
 * Created by lifh on 16/12/14.
 */
public interface ConstZk {
    String EXCHANGE_YXT = "/exchange_yxt"; //云算力兑换云鑫通参数
    String GAIN_TYPE = "/gain_type"; //云算力获取类型对应参数
    String SIGN = "/sign"; //云算力签到参数
    String YXT_AWARD = "/yxt_award";//云鑫通奖励


    String parseKey(String path, String... args);

}

