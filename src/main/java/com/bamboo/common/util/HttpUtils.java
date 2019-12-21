package com.bamboo.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/************
 * yunxin SMS TOOL:网易云信短信接口工具类
 * 
 * @author zjcjava@163.com time:2017.04.12
 *
 */
public class HttpUtils {

	
	public static final String PARAM_JSON = "JSON";
	public static final String PARAM_RAW = "RAW";
	public static final String CHART_SET = "utf-8";



	/*****
	 * 发送验证码消息
	 * 
	 * @param url url
	 * @param type 0 json 格式,1 参数格式
	 * @param headMap  head参数
	 * @param param  body参数
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static String httpPost(String url,String type,Map<String,String> headMap,Object param) throws IOException {


		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		

//		String curTime = String.valueOf((new Date().getTime() / 1000L));
//		String checkSum = YunxinUtils.getCheckSum(YunxinConstant.SMS_APP_SECRET, YunxinConstant.SMS_APP_NONCE, curTime);

		// 设置请求的header
		if(headMap!=null){
			  for (Entry<String, String> entry : headMap.entrySet()){
				  httpPost.addHeader(entry.getKey(), entry.getValue());
			  }
		}
//		post.addHeader("AppKey", YunxinConstant.SMS_APP_KEY);
//		post.addHeader("Nonce", YunxinConstant.SMS_APP_NONCE);
//		post.addHeader("CurTime", curTime);
//		post.addHeader("CheckSum", checkSum);
		
		switch (type) {
		case PARAM_JSON:
			httpPost.addHeader("Content-Type", "application/json");
			  // 将JSON进行UTF-8编码,以便传输中文
			String json=(String)param;
	        String encoderJson = URLEncoder.encode(json, CHART_SET);
			StringEntity se = new StringEntity(json);
	        se.setContentType("text/json");
	        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	        httpPost.setEntity(se);
			break;
		case PARAM_RAW:
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			// 设置请求参数
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			Map<String,String>  paramMap=(Map<String, String>) param;
			for (Entry<String, String> entry : paramMap.entrySet()){
				  nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			  }
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
			
			
			break;

		default:
			break;
		}
		
//		



		// 执行请求
		HttpResponse response = httpclient.execute(httpPost);
		String responseEntity = EntityUtils.toString(response.getEntity(), "utf-8");

		// status
		String httpStatusStr = response.getStatusLine().toString();
		String[] httpStatusArry = httpStatusStr.split(" ");
		String htttpCode = httpStatusArry.length > 2 ? httpStatusArry[1] : null;
//		System.out.println(htttpCode);
//		System.out.println(responseEntity);
		return responseEntity;
	}


	
	/*********
	 * 获取客户端请求的body内容
	 * @param request
	 * @return
	 */
	public static String getRequstBoty(HttpServletRequest request){
		InputStream is= null; 
		String contentStr=null; 
		try {
			is = request.getInputStream();
			contentStr= IOUtils.toString(is, "utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contentStr;
	}

	public static void main(String[] args) {
 
		
		 
	}

}
