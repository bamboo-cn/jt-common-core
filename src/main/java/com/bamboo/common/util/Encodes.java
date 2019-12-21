/**
 * Copyright (c) 2005-2012 springside.org.cn
 */
package com.bamboo.common.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 封装各种格式的(对称加密)编码解码工具类.<br>
 * 1.Commons-Codec的 hex/base64 编码<br>
 * 2.自制的base62 编码<br>
 * 3.Commons-Lang的xml/html escape<br>
 * 4.JDK提供的URLEncoder<br>
 * 5.自制的AES编码  aesEncode<br>
 * 6.自制的字节数组和16进制节码互转 hex2Byte<br>
 * 
 * @author calvin
 * @date 2013-01-15
 * @version V1.0
 */
public class Encodes {

	private static final String DEFAULT_URL_ENCODING = "UTF-8";
	private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
			.toCharArray();

	/**
	 * Hex编码.
	 */
	public static String encodeHex(byte[] input) {
		return new String(Hex.encodeHex(input));
	}

	/**
	 * Hex解码.
	 */
	public static byte[] decodeHex(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * Base64编码.
	 */
	public static String encodeBase64(byte[] input) {
		return new String(Base64.encodeBase64(input));
	}

	/**
	 * Base64编码.
	 */
	public static String encodeBase64(String input) {
		try {
			return new String(Base64.encodeBase64(input
					.getBytes(DEFAULT_URL_ENCODING)));
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	// /**
	// * Base64编码, URL安全(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548).
	// */
	// public static String encodeUrlSafeBase64(byte[] input) {
	// return Base64.encodeBase64URLSafe(input);
	// }

	/**
	 * Base64解码.
	 */
	public static byte[] decodeBase64(String input) {
		return Base64.decodeBase64(input.getBytes());
	}

	/**
	 * Base64解码.
	 */
	public static String decodeBase64String(String input) {
		try {
			return new String(Base64.decodeBase64(input.getBytes()),
					DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * Base62编码。
	 */
	public static String encodeBase62(byte[] input) {
		char[] chars = new char[input.length];
		for (int i = 0; i < input.length; i++) {
			chars[i] = BASE62[((input[i] & 0xFF) % BASE62.length)];
		}
		return new String(chars);
	}

	/**
	 * Html 转码.
	 */
	public static String escapeHtml(String html) {
		return StringEscapeUtils.escapeHtml4(html);
	}

	/**
	 * Html 解码.
	 */
	public static String unescapeHtml(String htmlEscaped) {
		return StringEscapeUtils.unescapeHtml4(htmlEscaped);
	}

	/**
	 * Xml 转码.
	 */
	public static String escapeXml(String xml) {
		return StringEscapeUtils.escapeXml10(xml);
	}

	/**
	 * Xml 解码.
	 */
	public static String unescapeXml(String xmlEscaped) {
		return StringEscapeUtils.unescapeXml(xmlEscaped);
	}

	/**
	 * URL 编码, Encode默认为UTF-8.
	 */
	public static String urlEncode(String part) {
		try {
			return URLEncoder.encode(part, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * URL 解码, Encode默认为UTF-8.
	 */
	public static String urlDecode(String part) {

		try {
			return URLDecoder.decode(part, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * AES 编码, Encode默认为UTF-8.
	 */
	public static String aesEncode(String msg, String key) {
		SecretKeySpec keySpec = new SecretKeySpec(hex2Byte(key.getBytes(Charset
				.forName(DEFAULT_URL_ENCODING))), "AES");
		String  encode=null;
		
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			encode= byte2Hex(cipher.doFinal(msg.getBytes(Charset.forName(DEFAULT_URL_ENCODING))));
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return encode;
	}

	/**
	 * AES 解码, Encode默认为UTF-8.
	 */
	public static String aesDecode(String msg, String key)  {
		SecretKeySpec secretKey = new SecretKeySpec(
				hex2Byte(key.getBytes(Charset.forName(DEFAULT_URL_ENCODING))), "AES");
		String  decode=null;
		
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			
			decode=new String(cipher.doFinal(hex2Byte(msg.getBytes(Charset
					.forName(DEFAULT_URL_ENCODING)))));
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return decode;

	}

	/*********
	 * 字节数组按照16进制形式转换为字符串
	 * 
	 * @Title: byte2Hex
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param bytes
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	private static String byte2Hex(byte[] bytes) {
		StringBuilder hexs = new StringBuilder();
		String s = "";
		for (int i = 0; i < bytes.length; i++) {
			s = Integer.toHexString(bytes[i] & 0XFF);
			if (s.length() == 1) {
				hexs.append("0");
			}
			hexs.append(s);
		}
		return hexs.toString();
	}

	/**********
	 * 按照16进制形式转换为字节数组
	 * 
	 * @Title: hex2Byte
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param b
	 * @param @return 设定文件
	 * @return byte[] 返回类型
	 * @throws
	 */
	private static byte[] hex2Byte(byte[] b) {
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	
	
	
	public static void main(String[] args) throws Exception {  
		String ASE_KEY=getAesKey();
		System.out.println("密钥 ：" + ASE_KEY);  
		String content = "21345243524999999999999999999991";
        // 加密  s
        System.out.println("加密前：" + content);  
        String encryptResult = aesEncode(content,ASE_KEY);  
          
        System.out.println("加密后：" + new String(encryptResult));  
        // 解密  
        String decryptResult = aesDecode(encryptResult,ASE_KEY);  
        System.out.println("解密后：" + new String(decryptResult));  
        
        /*
        
        long timeStamp = System.currentTimeMillis();
		String tokent = ECDesUtils.encode(ECDesUtils.AUHTON_KEY, timeStamp
				+ ECDesUtils.AUHTON_KEY);// 加密token
		String cookieStr = member.getMemberId().toString() + "_" + timeStamp
				+ "_" + tokent;*/
    }  
	
	/** 
     * 自动生成AES128位密钥 
     */  
    public static String getAesKey(){
    	String s=null;
        try {  
            KeyGenerator kg = KeyGenerator.getInstance("AES");  
            kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256  
            SecretKey sk = kg.generateKey();  
            byte[] b = sk.getEncoded();  
            s = byte2Hex(b);  
//            System.out.println(s);  
//            System.out.println("十六进制密钥长度为"+s.length());  
//            System.out.println("二进制密钥的长度为"+s.length()*4);  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
            System.out.println("没有此算法。");  
        }
		return s;
    }  
	
	
}
