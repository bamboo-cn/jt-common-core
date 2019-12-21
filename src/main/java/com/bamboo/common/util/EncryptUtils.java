package com.bamboo.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**   
 * @Title: EndecryptUtils.java 
 * @Package com.bamboo.common.utils 
 * @Description: 单向加密（摘要）工具类:spring注解需要加入spring bean的生命周期中使用<br>
 * 	<p>对称加密参考{@link Encodes} </P>
 * @author bamboo  <a href="zjcjava@163.com?subject=hello,bamboo&body=Dear Bamboo:%0d%0a描述你的问题：">Bamboo</a>   
 * @date 2017年6月2日 下午3:00:54 
 * @version V1.0   
 */
@Component
public class EncryptUtils {
	
	
	
	public static final String MD2 = "MD2";
    public static final String MD5 = "MD5";
    public static final String SHA_1 = "SHA1";
    public static final String SHA_256 = "SHA256";
    public static final String SHA_384 = "SHA384";
    public static final String SHA_512 = "SHA512";
    public static final String SHA1_MD5 = "SHA1_MD5";//先SHA1再MD5
    public static String ENCRYPT_SALT=""  ;//盐值:读取配置文件中的值encryptSalt
    public static Integer ENCRYPT_TIMES=1  ;//加密次数:读取配置文件中的值encryptStimes
	
	// @Value("#{config}")
	 public void init(Properties sys){
		 String encryptSalt=sys.getProperty("encryptSalt");
		 ENCRYPT_SALT=(encryptSalt==null||encryptSalt.length()<1)?"":encryptSalt;
		 String encryptStimes=sys.getProperty("encryptStimes");
		 ENCRYPT_TIMES=(encryptStimes==null||encryptStimes.length()<1)?1:Integer.valueOf(encryptStimes);
	 }  
	 
	 
    /**
     *  字符串摘要工具
     * @param sourceStr    需要加密目标字符串
     * @param algorithmsName 算法名称(如:MD2,MD5,SHA1,SHA256,SHA384,SHA512)
     * @param salt 盐值：可选项:如果在配置文件中配置了则取配置文件中的值,当方法参数中也有值时则按参数中的值
     * @param times 加密次数：可选项:默认加密一次
     * @return
     */
    public static String digestUtils(String sourceStr,String algorithmsName,String ...s){
        String password = "";
        String salt=(s.length<1||s[0]==null)?ENCRYPT_SALT:s[0];
        int times=(s.length<2||s[1]==null)?ENCRYPT_TIMES:Integer.valueOf(s[1]);
        switch(algorithmsName){
        case "MD2":
            password = DigestUtils.md2Hex(sourceStr);
            break;
        case "MD5":
            password = DigestUtils.md5Hex(sourceStr);
            break;
        case "SHA1":
            password = DigestUtils.sha1Hex(sourceStr);
            break;
        case "SHA256":
            password = DigestUtils.sha256Hex(sourceStr);
            break;
        case "SHA384":
            password = DigestUtils.sha384Hex(sourceStr);
            break;
        case "SHA512":
            password = DigestUtils.sha512Hex(sourceStr);
        case "SHA1_MD5":
        	password =DigestUtils.md5Hex(DigestUtils.sha1Hex(sourceStr)+salt) ;
    		for(;times>1;times--){
    			password =DigestUtils.md5Hex(password) ;
    		}
        
            break;
        }
        return password;
    }
    
    
    public static void  main(String [] args ) {
    	//String sha=digestUtils("123456","SHA1");
    	//System.out.println(digestUtils("123456","SHA1_MD5","HJN"));
    	System.out.println("l4XL58ZiS6ECy1MIRjH8WtgdOpUANEdY".length());
    	
    	
	}
    
    
    
}

