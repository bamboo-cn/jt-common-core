package com.bamboo.common.constant;

/**
 * Created by lifh on 16/11/30.
 */
public interface ConstRedis {
    //key
    String LAST_TRANS_PRICE = "last:trans:cms";

    String NEWS_DETAIL = "news:detail";

    //lock

    String parseKey(String path, Object... args);

}
