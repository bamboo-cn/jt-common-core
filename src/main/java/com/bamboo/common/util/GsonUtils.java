package com.bamboo.common.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Gson工具类
 * @author zhanghanlong
 * @eamil zjcjava@163.com
 * @time 2020/1/17 10:17
 */
public class GsonUtils {


    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static Gson gson = null;

    /**
     * 单例模式
     */
    static {
        if (gson == null) {
            //gson = new Gson();
            //当使用GsonBuilder方式时属性为空的时候输出来的json字符串是有键值key的,显示形式是"key":null，而直接new出来的就没有"key":null的
            gson= new  GsonBuilder().serializeNulls().disableHtmlEscaping().setDateFormat(DATE_FORMAT).create();
        }
    }


    /**
     * 将对象转成json格式
     *
     * @param object
     * @return String
     */
    public static String toJSONString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }



    /**
     * 使用Gson进行解析Bean
     *
     */
    public static <T> T parseObject(String jsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            //传入json对象和对象类型,将json转成对象
            t = gson.fromJson(jsonString, cls);
        }
        return t;
    }

    /**
     * 使用Gson进行解析 List<Beans>
     * @param gsonString
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> parseObjectList1(String gsonString, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            //根据泛型返回解析指定的类型,TypeToken<List<T>>{}.getType()获取返回类型
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }


    /**
     * json字符串转成list
     *
     * @param jsonStr
     * @param cls
     * @return
     */
    public static <T> List<T> parseObjectList(String jsonStr, Class<T> cls) {
        ArrayList<T> mList = new ArrayList<T>();  

        JsonArray array = new JsonParser().parse(jsonStr).getAsJsonArray();
        for(final JsonElement elem : array){
            mList.add(gson.fromJson(elem, cls));  
        }  
        return mList; 
    }
    /**
     * json字符串转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> parseListMap(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * json字符串转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> parseMap(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }



}
