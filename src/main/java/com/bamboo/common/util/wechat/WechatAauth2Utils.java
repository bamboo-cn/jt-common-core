package com.bamboo.common.util.wechat;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title: WechatAauth2Utils.java
 * @Package com.bamboo.common.util
 * @Description: 微信工具类:微信第三方登陆授权(适用于微信服务号)
 *               <ul>
 *               <li>获取微信第三方登陆授权</li>
 *               </ul>
 *
 * @author bamboo <a
 *         href="zjcjava@163.com?subject=hello,bamboo&body=Dear Bamboo:%0d%0a描述你的问题："
 *         >Bamboo</a>
 * @date 2017年6月2日 下午3:00:54
 * @version V1.0
 */
public class WechatAauth2Utils {


	private static Logger logger = LoggerFactory.getLogger(WechatAauth2Utils.class);


	/*****
	 * 1根据code获取AccessToken
	 * @Title: getAccessToken
	 * @Description: 1获取token
	 * @param @param appid
	 * @param @param appSecret
	 * @param @param code
	 * @param @return 设定文件
	 * @return accessToken 返回accessToken
	 * @throws
	 */
	public static Map<String, String> getAccessToken(String appid,
			String appSecret, String code) {
		// String appid="你公众号基本设置里的应用id";//应用ID
		// String appSecret="你公众号基本设置里的应用密钥";//(应用密钥)
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ appid + "&secret=" + appSecret + "&code=" + code
				+ "&grant_type=authorization_code";
		String backData = WechatJsdkUtils.sendGet(url, "utf-8", 10000);
		String accessToken = (String) JSONObject.fromObject(backData).get(
				"access_token");
		String openid = (String) JSONObject.fromObject(backData).get("openid");
		Map<String, String> map = new HashMap<String, String>();
		map.put("accessToken", accessToken);
		map.put("openid", openid);
		map.put("refresh_token",
				(String) JSONObject.fromObject(backData).get("refresh_token"));
		return map;

	}

	/*******
	 * 2根据accessToken获取用户信息
	 * <p>
	 * 拉取用户信息(需scope为 snsapi_userinfo)
	 * </p>
	 *
	 * @param accessToken
	 * @param openid
	 * @return
	 */
	public static Map<String, String> getUserinfo(String accessToken,
			String openid) {
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token="
				+ accessToken + "&openid=" + openid + "";
		String backData = WechatJsdkUtils.sendGet(url, "utf-8", 10000);
		logger.debug("[微信登陆获取用户信息]"+backData);

		String nickname = (String) JSONObject.fromObject(backData).get(
				"nickname");
		openid = (String) JSONObject.fromObject(backData).get("openid");
		String headimgurl = (String) JSONObject.fromObject(backData).get(
				"headimgurl");
		String sex = String.valueOf(JSONObject.fromObject(backData).get("sex"));
		Map<String, String> map = new HashMap<String, String>();
		map.put("nickname", nickname);
		map.put("openid", openid);
		map.put("headimgurl", headimgurl);
		map.put("sex", sex);
		return map;
	}

	/**********
	 * 刷新获取access_token
	 *
	 * @param appid
	 * @param grant_type
	 * @param refresh_token
	 * @return
	 */
	public static Map<String, String> refreshToken(String appid,
			String grant_type, String refresh_token) {
		// String appid="你公众号基本设置里的应用id";//应用ID
		String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="
				+ appid
				+ "&grant_type="
				+ grant_type
				+ "&refresh_token="
				+ refresh_token;
		String backData = WechatJsdkUtils.sendGet(url, "utf-8", 10000);
		String accessToken = (String) JSONObject.fromObject(backData).get(
				"access_token");
		String openid = (String) JSONObject.fromObject(backData).get("openid");
		Map<String, String> map = new HashMap<String, String>();
		map.put("accessToken", accessToken);
		map.put("openid", openid);
		return map;
	}

	/********
	 * 重新获取access_token
	 *
	 * @param access_token
	 * @param openid
	 * @return
	 */
	public static Long checkAccessToken(String access_token, String openid) {
		String url = "https://api.weixin.qq.com/sns/auth?access_token="
				+ access_token + "&grant_type=" + openid;
		String backData = WechatJsdkUtils.sendGet(url, "utf-8", 10000);
		String errorCode = String.valueOf(JSONObject.fromObject(backData).get(
				"errcode"));

		return Long.valueOf(errorCode);
	}

	public static void main(String[] args) {
		String appid = "ur appid";
		String appSecret = "appSecret";
		String code = "client user code";
		Map<String, String> accessToken = getAccessToken(appid, appSecret, code);
		// System.out.println(accessToken.get("accessToken"));
		// Map<String, String> accessToken = refreshToken(appid,
		// "refresh_token", accessToken.get("refresh_token"));
		// Long checkAccessToken =
		// checkAccessToken(accessToken.get("accessToken"),
		// accessToken.get("openid"));
		System.out.println(accessToken);
		if (accessToken != null) {
			// String userinfo =
			// getUserinfo("6__2SVxFNLj5pG0G8Nw1FXL_sen6JPUCNui8AHO_jGtE2NMftAq2f1hTkqHvWWaU42RSvmGBXoGn6SnewgGXN0tQ","ok5-K1hTMdPb0o7ePPT_bSvZWyWE");
			Map<String, String> userinfo = getUserinfo(
					accessToken.get("accessToken"), accessToken.get("openid"));
			System.out.println(userinfo);
		}
		// System.out.println(accessToken);
		// getUserinfo
	}

}
