package com.bamboo.common.util;

import com.alibaba.fastjson.JSON;
import com.bamboo.common.constant.Constant;
import com.google.common.collect.Maps;
import com.xialeme.common.core.result.ResultInfo;
import net.sf.json.JSONObject;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;


/**
 * 微信支付工具
 * @Title: WechatPayUtil.java
 * @Package com.bamboo.common.utils
 * @author bamboo  zjcjava@163.com
 * @date 2018年2月12日
 * @version V1.0
 */
public class WechatPayUtils {
	
	private static Logger logger = LoggerFactory.getLogger(WechatPayUtils.class);
	    /**
	     * 根据code获取openid
	     * @param code
	     * @return
	     * @throws IOException
	     */
	     public static Map<String,Object> getOpenIdByCode(String code) throws IOException {
	        //请求该链接获取access_token
	        HttpPost httppost = new HttpPost("https://api.weixin.qq.com/sns/oauth2/access_token");
	        //组装请求参数
	        String reqEntityStr = "appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	        reqEntityStr = reqEntityStr.replace("APPID", WechatConstants.APPID);
	        reqEntityStr = reqEntityStr.replace("SECRET", WechatConstants.APP_SECRET);
	        reqEntityStr = reqEntityStr.replace("CODE", code);
	        StringEntity reqEntity = new StringEntity(reqEntityStr);
	        //设置参数
	        httppost.setEntity(reqEntity);
	        //设置浏览器
	        CloseableHttpClient httpclient = HttpClients.createDefault();
	        //发起请求
	        CloseableHttpResponse response = httpclient.execute(httppost);
	        //获得请求内容
	        String strResult = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
	        //获取openid
	        Map<String,Object> map = new HashMap<String,Object>();
	        map.put("openid",(String) JSONObject.fromObject(strResult).get("openid"));
	        map.put("access_token",(String) JSONObject.fromObject(strResult).get("access_token"));
	        map.put("refresh_token",(String) JSONObject.fromObject(strResult).get("refresh_token"));
	        return map;
	    }
	     
	     
	    /**
	     * 1.统一下预付单
	     * @param out_trade_no 自己生成的订单号
	     * @param descript 商品描述
	     * @param total_fee 订单总金额，单位为分
	     * * @param openid 用户微信登陆的用户标识
	     * @param IP 用户端实际ip
	     * @return
	     * @throws IOException
	     */
	    public static ResultInfo unifiedOrder(String out_trade_no,String descript,Integer total_fee,String openid,String IP)throws IOException {
	      
	    	ResultInfo resultInfo = new ResultInfo();
	        String nonce_str = getNonceStr().toUpperCase();//随机
	        //组装请求参数,按照ASCII排序
	        String sign = "appid=" + WechatConstants.APPID +
	                        "&body=" + descript +
	                        "&mch_id=" + WechatConstants.MCH_ID +
	                        "&nonce_str=" + nonce_str +
	                        "&notify_url=" + WechatConstants.NOTIFY_URL +
	                        "&openid=" + openid +
	                        "&out_trade_no=" + out_trade_no +
	                        "&spbill_create_ip=" + IP +
	                        "&total_fee=" + total_fee.toString() +
	                        "&trade_type=" + WechatConstants.TRADE_TYPE_JSAPI + 
	                        "&key=" + WechatConstants.KEY;//这个字段是用于之后MD5加密的，字段要按照ascii码顺序排序
	        sign = EncryptUtils.digestUtils(sign,"MD5").toUpperCase();

	        //组装包含openid用于请求统一下单返回结果的XML
	        StringBuilder sb = new StringBuilder("");
	        sb.append("<xml>");
	        setXmlKV(sb,"appid",WechatConstants.APPID);
	        setXmlKV(sb,"body",descript);
	        setXmlKV(sb,"mch_id",WechatConstants.MCH_ID);
	        setXmlKV(sb,"nonce_str",nonce_str);
	        setXmlKV(sb,"notify_url",WechatConstants.NOTIFY_URL);
	        setXmlKV(sb,"openid",openid);
	        setXmlKV(sb,"out_trade_no",out_trade_no);
	        setXmlKV(sb,"spbill_create_ip",IP);
	        setXmlKV(sb,"total_fee",total_fee.toString());
	        setXmlKV(sb,"trade_type",WechatConstants.TRADE_TYPE_JSAPI);
	        setXmlKV(sb,"sign",sign);
	        sb.append("</xml>");
	        
	        logger.debug("[微信统一下单xml:]"+sb.toString());  
	        //发送请求
            byte[] xmlData = sb.toString().getBytes();
            URL url = new URL(WechatConstants.UNIFIED_ORDER_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("Content_Type","text/xml");
            urlConnection.setRequestProperty("Content-length",String.valueOf(xmlData.length));
            DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());
            outputStream.write(xmlData);
            outputStream.flush();
            outputStream.close();
            
            SortedMap<String,Object> resultMap = parseXml(urlConnection.getInputStream());
            logger.debug("[微信统一下单返回结果:]"+JSON.toJSON(resultMap));  
            
            
            //1.2.统一下单接口返回正常的prepay_id，再按签名规范重新生成签名后，将数据传输给前端
            String returnCode = (String) resultMap.get("return_code");//通信标识
	        String resultCode = (String) resultMap.get("result_code");//交易标识
	        //只有当returnCode与resultCode均返回“success”，才代表微信支付统一下单成功
	        if (WechatConstants.RETURN_SUCCESS.equals(resultCode)&&WechatConstants.RETURN_SUCCESS.equals(returnCode)){
	        	
	        	Map<String,Object> map = new HashMap<String,Object>();
	        	String prepay_id = (String) resultMap.get("prepay_id");//统一下单返回的预支付id
		        String timeStamp = String.valueOf((System.currentTimeMillis()/1000));//1970年到现在的秒数
		        String nonceStr = getNonceStr().toUpperCase();//随机数据字符串
		        String packageStr = "prepay_id=" + prepay_id;
		        String signType = "MD5";
		        String paySign =
		                "appId=" + WechatConstants.APPID +
		                "&nonceStr=" + nonceStr +
		                "&package=prepay_id=" + prepay_id +
		                "&signType=" + signType +
		                "&timeStamp=" + timeStamp +
		                "&key="+ WechatConstants.KEY;//注意这里的参数要根据ASCII码 排序
		        paySign = EncryptUtils.digestUtils(paySign,"MD5").toUpperCase();//将数据MD5加密

		        map.put("appId",WechatConstants.APPID);
		        map.put("timeStamp",timeStamp);
		        map.put("nonceStr",nonceStr);
		        map.put("packageStr",packageStr);
		        map.put("signType",signType);
		        map.put("paySign",paySign);
		        map.put("prepayId",prepay_id);
	        	
		        
		        resultInfo=new ResultInfo(Constant.SUCCESS, "微信统一下单成功", map);
		        logger.info("[微信统一下单成功]"+JSON.toJSON(map));  
		        
	        }else {
	        	
	        	resultInfo=new ResultInfo(Constant.FAIL, "微信统一下单失败,订单编号:"+out_trade_no+",失败原因:"+resultMap.get("err_code_des"), null);
	        	logger.info("[微信统一下单失败]失败原因:"+resultMap.get("err_code_des"));  
	        }
            
            
            
            
	        return resultInfo;
	    }
	    
	    /**
	     * 解析微信服务器发来的请求
	     * @param inputStream
	     * @return 微信返回的参数集合
	     */
	    public static SortedMap<String,Object> parseXml(InputStream inputStream) {
	        SortedMap<String,Object> map = Maps.newTreeMap();
	        try {
	            //获取request输入流
	            SAXReader reader = new SAXReader();
	            Document document = reader.read(inputStream);
	            //得到xml根元素
	            Element root = document.getRootElement();
	            //得到根元素所有节点
	            List<Element> elementList = root.elements();
	            //遍历所有子节点
	            for (Element element:elementList){
	                map.put(element.getName(),element.getText());
	            }
	            //释放资源
	            inputStream.close();
	        } catch (Exception e) {
//	            throw new Exception("微信工具类:解析xml异常",e);
				logger.error("微信工具类:解析xml异常{}",e);
	        }
	        return map;
	    }
	    
    
	    
	    /**
	     * 修改订单状态，获取微信回调结果
	     * @param request
	     * @return
	     */
	    public static ResultInfo getNotifyResult(HttpServletRequest request){
	        String inputLine;  
	        String notifyXml = "";
	        String resXml = "";
	        
	        ResultInfo resultInfo = new ResultInfo();
	        
	        try {  
	            while ((inputLine = request.getReader().readLine()) != null){  
	                notifyXml += inputLine;  
	            }  
	            request.getReader().close();  
	        } catch (Exception e) {  
	            //logger.debug("[微信支付]xml获取失败：" + e);
	            e.printStackTrace();
	        }
	        logger.debug("[微信支付-回调]接收到的xml：" + notifyXml);
	        //logger.debug("[微信支付]收到微信异步回调：");  
	        if(StringUtils.isEmpty(notifyXml)){
	            logger.debug("[微信支付-回调]xml为空：");  
	        }

	        String appid = getXmlPara(notifyXml,"appid");;  
	        String bank_type = getXmlPara(notifyXml,"bank_type");  
	        String cash_fee = getXmlPara(notifyXml,"cash_fee");
	        String fee_type = getXmlPara(notifyXml,"fee_type");  
	        String is_subscribe = getXmlPara(notifyXml,"is_subscribe");  
	        String mch_id = getXmlPara(notifyXml,"mch_id");  
	        String nonce_str = getXmlPara(notifyXml,"nonce_str");  
	        String openid = getXmlPara(notifyXml,"openid");  
	        String out_trade_no = getXmlPara(notifyXml,"out_trade_no");//商家系统生成的订单号
	        String result_code = getXmlPara(notifyXml,"result_code");
	        String return_code = getXmlPara(notifyXml,"return_code");
	        String sign = getXmlPara(notifyXml,"sign");
	        String time_end = getXmlPara(notifyXml,"time_end");
	        String total_fee = getXmlPara(notifyXml,"total_fee");
	        String trade_type = getXmlPara(notifyXml,"trade_type");
	        String transaction_id = getXmlPara(notifyXml,"transaction_id");//微信订单ID
	        
	        
	        Map<String,String> map = new HashMap<String,String>();
	        map.put("transaction_id", transaction_id);
	        map.put("out_trade_no", out_trade_no);

	        //根据返回xml计算本地签名
	        String localSign =
	                "appid=" + appid +
	                "&bank_type=" + bank_type +
	                "&cash_fee=" + cash_fee +
	                "&fee_type=" + fee_type +
	                "&is_subscribe=" + is_subscribe +
	                "&mch_id=" + mch_id +
	                "&nonce_str=" + nonce_str +
	                "&openid=" + openid +
	                "&out_trade_no=" + out_trade_no +
	                "&result_code=" + result_code +
	                "&return_code=" + return_code +
	                "&time_end=" + time_end +
	                "&total_fee=" + total_fee +
	                "&trade_type=" + trade_type +
	                "&transaction_id=" + transaction_id +
	                "&key=" + WechatConstants.KEY;//注意这里的参数要根据ASCII码 排序
	        localSign =  EncryptUtils.digestUtils(localSign,"MD5").toUpperCase();//将数据MD5加密

	        //logger.debug("[微信支付]本地签名是：" + localSign);  
	        //logger.debug("[微信支付]微信支付签名是：" + sign);

	        //本地计算签名与微信返回签名不同||返回结果为不成功
	        if(!sign.equals(localSign) || !"SUCCESS".equals(result_code) || !"SUCCESS".equals(return_code)){
	            logger.error("[微信支付-回调]验证签名失败或返回错误结果码");
	            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[FAIL]]></return_msg>" + "</xml> ";
	        
	            resultInfo=new ResultInfo(Constant.FAIL, resXml, map);
	        }else{
	             System.out.println("支付成功");
	             logger.debug("[微信支付-回调]公众号支付成功，out_trade_no(订单号)为：" + out_trade_no);
	             resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
	             resultInfo=new ResultInfo(Constant.SUCCESS, resXml, map);
	        }
	        return resultInfo;
	    }
	    
	    
	    /**
	     * 获取32位随机字符串
	     * @return
	     */
	    public static String getNonceStr(){
	        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	        StringBuilder sb = new StringBuilder();
	        Random rd = new Random();
	        for(int i = 0 ; i < 32 ; i ++ ){
	            sb.append(str.charAt(rd.nextInt(str.length())));
	        }
	        return sb.toString();
	    }
	    /**
	     * 插入XML标签
	     * @param sb
	     * @param Key
	     * @param value
	     * @return
	     */
	    public static StringBuilder setXmlKV(StringBuilder sb,String Key,String value){
	        sb.append("<");
	        sb.append(Key);
	        sb.append(">");

	        sb.append(value);

	        sb.append("</");
	        sb.append(Key);
	        sb.append(">");

	        return sb;
	    }

	    /**
	     * 解析XML 获得名称为para的参数值
	     * @param xml
	     * @param para
	     * @return
	     */
	    public static String getXmlPara(String xml,String para){
	        int start = xml.indexOf("<"+para+">");
	        int end = xml.indexOf("</"+para+">");

	        if(start < 0 && end < 0){
	            return null;
	        }
	        return xml.substring(start + ("<"+para+">").length(),end).replace("<![CDATA[","").replace("]]>","");
	    }

	    /**
	     * 获取ip地址
	     * @param request
	     * @return
	     */
	    public static String getIpAddr(HttpServletRequest request) {  
	    	String ip = request.getHeader("x-forwarded-for");  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("Proxy-Client-IP");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("WL-Proxy-Client-IP");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getRemoteAddr();  
	        }  
	        return ip;
	    }
	    
	    /**
	     * 生成订单号
	     * @return
	     */
	    public static String getTradeNo() {  
	    	 int rannum = (int) (RandomUtils.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
	    	String out_trade_no=DateUtils.getDate("yyyyMMddHHmmssSSS")+rannum;
	    	
	    	return out_trade_no;
	    }
	    
	    
	    public static void  main(String [] args ) {
	    	
	    	
	    	String openid="oE7BfxKpgKlmsZejSRYCY4KZNB00";
	    	String out_trade_no=getTradeNo();
	    	int total_fee=1;
	    	String descript="测试";
	    	String IP="192.xxx.11.1";//公网测试白名单
	    	
	    	//下单
	    	try {
			unifiedOrder(out_trade_no,descript,  total_fee, openid, IP);
			//{"nonce_str":"R8ykNX73Z0ocSo71","appid":"wx3c98fe53457e1f3d","sign":"1612EEAA1C62EFB68969F0A6102F0682","trade_type":"JSAPI","result_code":"SUCCESS","return_msg":"OK","mch_id":"1461496402","return_code":"SUCCESS","prepay_id":"wx201802122157164c7fc881410241097916"}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	System.out.println(111);
	    	
		}
}

