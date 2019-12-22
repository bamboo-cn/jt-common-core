package com.bamboo.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

/**
 * @Title: WechatUtils.java
 * @Package com.bamboo.common.utils
 * @Description: 微信工具类
 * 				<ul>	
 * 					<li>getJsTokenConfig(String appid, String appSecret, String url)获取微信js授权的config</li>
 * 				</ul>
 * 					
 * @author bamboo <a
 *         href="zjcjava@163.com?subject=hello,bamboo&body=Dear Bamboo:%0d%0a描述你的问题："
 *         >Bamboo</a>
 * @date 2017年6月2日 下午3:00:54
 * @version V1.0
 */
public class WechatJsdkUtils {
	
	
	/**  WechatUtils 常量WECHAT_JSAPI_TICKET 缓存中使用 ***/
	public static final String WECHAT_JSAPI_TICKET = "WECHAT_JSAPI_TICKET";
	/**  WECHAT_JSAPI_TICKET 缓存间长，默认为7200秒这里设置为6000秒***/
	public static final long WECHAT_JSAPI_TICKET_INTERVAL =6000;
	
	
    /***
     * 模拟get请求
     * @param url
     * @param charset
     * @param timeout
     * @return
     */
     public static String sendGet(String url, String charset, int timeout)
      {
        String result = "";
        try
        {
          URL u = new URL(url);
          try
          {
            URLConnection conn = u.openConnection();
            conn.connect();
            conn.setConnectTimeout(timeout);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
            String line="";
            while ((line = in.readLine()) != null)
            {
             
              result = result + line;
            }
            in.close();
          } catch (IOException e) {
            return result;
          }
        }
        catch (MalformedURLException e)
        {
          return result;
        }
       
        return result;
      }
     
     
     
     /*****
     * @Title: getAccessToken 
     * @Description: 1获取token 
     * @param @param appid
     * @param @param appSecret
     * @param @return    设定文件 
     * @return accessToken    返回accessToken
     * @throws
      */
     public static String getAccessToken( String appid,String appSecret){
//            String appid="你公众号基本设置里的应用id";//应用ID
//            String appSecret="你公众号基本设置里的应用密钥";//(应用密钥)
            String url ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appSecret;
            String backData=sendGet(url, "utf-8", 10000);
            String accessToken = (String) JSONObject.fromObject(backData).get("access_token");  
            return accessToken;
     }
    /********
    * @Title: getJSApiTicket 
    * @Description: 2用token获取jsTicket
    * @param @param acess_token
    * @param @return    设定文件 
    * @return String    返回类型 
    * @throws
     */
    public static String getJSApiTicket(String acess_token){ 
        //获取token
       // String acess_token= getAccessToken();
           
        String urlStr = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+acess_token+"&type=jsapi";  
        String backData=sendGet(urlStr, "utf-8", 10000);  
        String ticket = (String) JSONObject.fromObject(backData).get("ticket");  
        return  ticket;  
           
    }  
    /********
     * @Title: getJSApiTicket 
     * @Description: 3用appid,appSecret获取jsapi_ticket
     * @param @param acess_token
     * @param @return    设定文件 
     * @return String    返回类型 
     * @throws
     */
    public static String getJSApiTicket(String appid, String appSecret){ 
    	//获取token
    	String accessToken=getAccessToken( appid,appSecret);
    	if(accessToken==null){
    		new Exception("微信accessToken授权错误");
    		return null;
    	}
		String jsapi_ticket=getJSApiTicket(accessToken);
    	return  jsapi_ticket;  
    	
    }  
    
    /************
     * @Title: getJsTokenConfig 
     * @Description: 4.根据jsapi_ticket获取js凭证Config:私有方法
     * @param   appid
     * @param   jsapi_ticket
     * @param   url JS接口安全域名
     * @return Map<String,String>    返回类型 
     * @throws
      */
 	private static Map<String, String> getJsTokenConfig(String appid,String jsapi_ticket,String url) {
 		Map<String, String> ret = new HashMap<String, String>();
 		String nonce_str = create_nonce_str();
 		String timestamp = create_timestamp();
 		String string1;
 		String signature = "";
 		
 		if(jsapi_ticket==null){
			new Exception("微信jsapi_ticket为null");
		}

 		// 注意这里参数名必须全部小写，且必须有序
 		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str
 				+ "&timestamp=" + timestamp + "&url=" + url;
 		System.out.println(string1);

 		try {
 			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
 			crypt.reset();
 			crypt.update(string1.getBytes("UTF-8")); // 对string1 字符串进行SHA-1加密处理
 			signature = byteToHex(crypt.digest()); // 对加密后字符串转成16进制
 			//System.out.println("SS:"+signature);
 			
 		} catch (NoSuchAlgorithmException e) {
 			e.printStackTrace();
 		} catch (UnsupportedEncodingException e) {
 			e.printStackTrace();
 		}
 		

 		ret.put("appId", appid);
 		ret.put("url", url);
 		ret.put("jsapiTicket", jsapi_ticket);
 		ret.put("nonceStr", nonce_str);
 		ret.put("timestamp", timestamp);
 		ret.put("signature", signature);

 		return ret;
 	}

	
	/************
    * @Title: sign 
    * @Description: 5.根据acctToken,jsapi_ticket获取js凭证Config
    * @param appid
    * @param appSecret
    * @param jsapi_ticket 从缓存中传过来的有效时间为7200秒，如果没有则直接为nul即可
    * @param url JS接口安全域名
    * @return Map<String,String>    返回类型 
    * @throws
     */
	public static Map<String, String> getJsTokenConfig(String appid, String appSecret, String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		
		 
		if(jsapi_ticket==null){//如果为空则从新获取
			jsapi_ticket=getJSApiTicket(appid,appSecret);//获取jsapi_ticket
		}
		ret = getJsTokenConfig(appid, jsapi_ticket, url);//获取js的config

		return ret;
	}
	
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	// 生成随机字符串
	private static String create_nonce_str() {
		return UUID.randomUUID().toString().replace("-","").substring(0,16);//随机字符串  
	}

	// 生成时间戳字符串
	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	public static void main(String[] args) {

		String appid="appid";
		String appSecret="appSecret";
		String url = "http://m.xx.com/MP_verify_xxxxdRT.txt";//微信给的证书验证文件地址
		String accessToken=getAccessToken(appid,appSecret);
		String jsapi_ticket=getJSApiTicket(accessToken);
		// 注意 URL 一定要动态获取，不能 hardcode
	/*	Map<String, String> ret = getJsTokenConfig(appid,appSecret, url);
		for (Map.Entry entry : ret.entrySet()) {
			System.out.println(entry.getKey() + ", " + entry.getValue());
		}
		
		System.out.println(create_nonce_str());*/
		
	}
	
	
}
