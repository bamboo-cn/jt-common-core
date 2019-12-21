package com.bamboo.common.util;


/**
 * 微信常量类
 * <p>TODO(用一句话描述该文件做什么)
 * @Title: WechatConstants.java
 * @Package com.bamboo.common.utils
 * @author bamboo  zjcjava@163.com
 * @date 2018年2月12日
 * @version V1.0
 */
public class WechatConstants {

	
	///常量类（根据商户信息进行设置
	 //第三方用户唯一ID
    public static String APPID = "wx3c98fe53457e1f3d";//下了么
    //第三方用户唯一凭证密码
    public static String APP_SECRET = "7a743efe6b1abcf686a67e57be336eea";
    //商户ID
    public static String MCH_ID = "1461496402";
    //微信商户平台-账户设置-安全设置-api安全,配置32位key
    public static String KEY  = "l4XL58ZiS6ECy1MIRjH8WtgdOpUANEdY";
    /**微信支付回调url:此处url用于接收微信服务器发送的支付通知，并处理商家的业务*/
    public static final String NOTIFY_URL = "http://api.vowall.com/web-api/api/weixin/notifyUrl";
    /**微信交易类型:公众号支付*/
    public static final String TRADE_TYPE_JSAPI = "JSAPI";
    /**微信交易类型:原生扫码支付*/
    public static final String TRADE_TYPE_NATIVE = "NATIVE";
    /**微信甲乙类型:APP支付*/
    public static final String TRADE_TYPE_APP = "APP";
    /**RETURN_SUCCESS*/
    public static final String RETURN_SUCCESS = "SUCCESS";
    
    /**微信统一下单url*/
    public static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**微信申请退款url*/
    public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    
    
    /***以下功能只有在商户支付时才使用****/
    /**微信支付api证书路径*/
    public static final String CERT_PATH = "***/apiclient_cert.p12";


    
}

