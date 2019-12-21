package com.bamboo.common.util;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @Title: EmailUtils.java
 * @Package com.bamboo.common.utils
 * @Description: 邮件工具类
 * 				<ul>	
 * 					<li>getJsTokenConfig(String appid, String appSecret, String url)获取邮件js授权的config</li>
 * 				</ul>
 * 					
 * @author bamboo <a
 *         href="zjcjava@163.com?subject=hello,bamboo&body=Dear Bamboo:%0d%0a描述你的问题："
 *         >Bamboo</a>
 * @date 2017年12月2日 下午3:00:54
 * @version V1.0
 */
public class EmailUtils {
	
	
	/**  EmailUtils 常量邮箱的协议类型***/
	public static String EMAIL_PROTOCOL = "smtp";//邮箱服务协议类型
	/**  EmailUtils 常量邮箱的服务器地址 ***/
	public static String EMAIL_HOST =  "smtp.mxhichina.com";//"smtp.sina.com.cn";//邮箱服务器地址"smtp.qq.com";//
	/**  EmailUtils 邮箱服务器端口 ***/
	public static String EMAIL_PORT =  "25";//邮箱服务器端口//"465";//
	/**  EmailUtils 邮箱服务器SSL开启 ***/
	public static String EMAIL_SSL_ENABLE =  "0";//邮箱服务器SSL开启:0否,1是,qq邮箱必须开启
	
	/**  EmailUtils 常量邮箱的账号昵称 ***/
	public static String EMAIL_USER_NICKNAME= "VOWALL志愿墙";//邮箱昵称
	/**  EmailUtils 常量邮箱的账号 ***/
	public static String EMAIL_USER = "service@vowall.com";//"vowallservice@sina.com";//邮箱账号"85745@qq.com";//
	/**  EMAIL_PASSWORD 邮箱的密码 ***/
	public static String EMAIL_PASSWORD ="vowall@2017";// "PASSWORD-vowall123";//邮箱密码"PASSWORD-udithodnjrvdbhja";//
	
    
     
     /**
      * 创建一封只包含文本的简单邮件
      *
      * @param session 和服务器交互的会话
      * @param sendMail 发件人邮箱
      * @param receiveMail 收件人邮箱
      * @return
      * @throws Exception
      */
     public static void createMimeMessage(Email email) {
         
    	 
    	 
    	 
    	 // 0. 创建参数配置, 用于连接邮件服务器的参数配置
         Properties props = new Properties();                    // 参数配置
         props.setProperty("mail.smtp.auth", "true");            // 需要请求认证:必须进行账号认证
    	 // 判断是否需要身份认证    
         
        	 try {
				// SSL加密  
				 MailSSLSocketFactory sf = null;  
				 sf = new MailSSLSocketFactory();  
				 // 设置信任所有的主机  
				 sf.setTrustAllHosts(true);  
				 
				 //是否开启SSL安全
				 if(EMAIL_SSL_ENABLE.equals("1")){
					 props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
					 props.put("mail.smtp.ssl.enable", "true");
					 props.put("mail.smtp.ssl.socketFactory", sf);
					 //端口检查：如果端口依然是25则修改为默认的SSL加密端口为465
					 EMAIL_PORT=EMAIL_PORT.equals("25")?"465":EMAIL_PORT;
				 }
				 
				 
				 props.setProperty("mail.transport.protocol", EMAIL_PROTOCOL);   // 使用的协议（JavaMail规范要求）
		         props.setProperty("mail.smtp.host", EMAIL_HOST);   // 发件人的邮箱的 SMTP 服务器地址
		         props.setProperty("mail.smtp.port", EMAIL_PORT);   // 发件人的邮箱的 SMTP 服务器端口
		         
		         
		         //设置账号密码认证
				 Session session = Session.getInstance(props, new Authenticator() {
				      // 认证信息，需要提供"用户账号","授权码"  
				      public PasswordAuthentication getPasswordAuthentication() {  
				        return new PasswordAuthentication(EMAIL_USER, EMAIL_PASSWORD);  
				      }  
				    });
				
				 //session.setDebug(true); // 设置为debug模式, 可以查看详细的发送 log
				 
				 // 1. 创建一封邮件
				 MimeMessage message = new MimeMessage(session);

				// 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
				 message.setFrom(new InternetAddress(EMAIL_USER, EMAIL_USER_NICKNAME, "UTF-8"));//email.getFromMail(), email.getFromName()

				 // 3. To: 收件人（可以增加多个收件人、抄送、密送）
				 // message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(EMAIL_USER, EMAIL_USER_NICKNAME, "UTF-8"));
				 if (email != null && !email.getToMail().isEmpty()) {
			            int size = email.getToMail().size();
			            InternetAddress[] addresses = new InternetAddress[size];
			            for (int i = 0; i < size; i++) {
			                addresses[i] = new InternetAddress(email.getToMail().get(i));
			            }
			            message.setRecipients(MimeMessage.RecipientType.TO, addresses);
			        }
				 
				//    Cc: 抄送（可选）
				// 设置抄送人的电子邮件
				//message.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress(email.getToMail(), email.getToName(), "UTF-8"));
				//    Bcc: 密送（可选）
				 //message.setRecipient(MimeMessage.RecipientType.BCC, new InternetAddress("ff@receive.com", "USER_FF", "UTF-8"));

				 // 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
				 message.setSubject(email.getSubject(), "UTF-8");

				 // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
				 message.setContent(email.getContent(), "text/html;charset=UTF-8");

				 // 6. 设置发件时间
				 message.setSentDate(new Date());

				 // 7. 保存设置
				 message.saveChanges();
				 
				// 发送邮件
		        Transport.send(message);
				 
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 

     }
    
	
	//email邮件类
	public static class Email implements Serializable {
		
		private static final long serialVersionUID = 32L;
		
		private  List<String> toMail;//收件人邮箱列表
		private String subject;// 邮件标题
		private String content;//邮件正文
		
		
		public Email() {
			super();
		}


		public Email(List<String> toMail, String subject, String content) {
			super();
			this.toMail = toMail;
			this.subject = subject;
			this.content = content;
		}


		public List<String> getToMail() {
			return toMail;
		}


		public void setToMail(List<String> toMail) {
			this.toMail = toMail;
		}



		public String getSubject() {
			return subject;
		}


		public void setSubject(String subject) {
			this.subject = subject;
		}


		public String getContent() {
			return content;
		}


		public void setContent(String content) {
			this.content = content;
		}
		
		
		
	}
	
	public static void main(String[] args) {


		List<String> toMail=new ArrayList<String>();
		toMail.add("zjcjava@163.com");
		toMail.add("18840859157@163.com");
		toMail.add("1361526299@qq.com");
		toMail.add("723247909@qq.com");
		toMail.add("348285745@qq.com");
		toMail.add("vowall@126.com");
		toMail.add("vowallservice@sina.com");
		
		
		String dateTimeStr=DateUtils.formatDateTime(new Date());
		String url="http://m.vowall.com";
		System.out.println(dateTimeStr);
		
		String content="<table cellpadding='0' cellspacing='0' style='width: 100%;font-family: 'Microsoft YaHei', '微软雅黑', 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB',  Arial, sans-serif;'><tbody><tr><td style='min-width: 760px;background: #eeeeee;text-align: center;'><div style='width: 680px;display: inline-block'><table cellpadding='0' cellspacing='0' style='width: 680px;margin: 0 auto;'><tbody><tr><td colspan='3' style='text-align: right;padding: 5px 0;'><span style='visibility:hidden; opacity:0; color:transparent; height:0; width:0;font-size:0;display: inline-block;'>请点击链接确认邮箱地址&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　---------------------------------------------------------------------------------------------------------------------------------------------------------</span></td></tr><tr><td colspan='3' style='background: #fff;text-align: center;'><img src='http://img.vowall.com//group1/M00/00/03/Ch8wMVopGviAcXJCAApK53ruMfA606.jpg' alt='' style='width: 100%;'></td></tr><tr><td style='width: 80px;background: #fff;'>&nbsp;</td><td style='text-align: center;background: #fff;font-family: 'Microsoft YaHei', '微软雅黑', 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB',  Arial, sans-serif;'><div style='border-radius: 0 0 3px 3px;background: #ffffff;'><br><h1 style='color: #4d4d4d;font-size: 18px;line-height: 31px;font-weight: normal;text-align: left;font-family: 'Microsoft YaHei', '微软雅黑', 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB',  Arial, sans-serif;'>尊敬的用户：<br>您好！</h1><table cellpadding='0' cellspacing='0' style='width: 100%;'><tbody><tr><td style='line-height: 40px;color: #43a047;font-size: 18px;text-align: left;font-family: 'Microsoft YaHei', '微软雅黑', 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB',  Arial, sans-serif;'>你需要点击验证该邮箱地址！</td><td style='text-align: right;'><a href='{$URL}' target='_blank' style='float: right;text-decoration: none;'><p style='width: 100px;height: 35px;border: 1px solid;padding: 5px;text-align: center;line-height: 35px;background: #42a5f6;color: #FFF;border-radius: 5px;'>点击验证</p></a></td></tr></tbody></table><p style='padding: 0;margin: 0;line-height: 20px;text-align: left;'>如果邮箱服务器拦截，请拷贝下面的地址到新打开的浏览器窗口中访问<br><pre>{$URL}</pre></p><p style='line-height: 28px;color: #90a4ae;font-size: 13px;text-align: left;font-family: 'Microsoft YaHei', '微软雅黑', 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB',  Arial, sans-serif;'>请根据您的使用场景，选择我们的产品。如有技术问题，您可以<a href='http://vowall.com' target='_blank' style='color: #42a5f5;'>点此</a>获取帮助。<br>感谢您对志愿墙平台的关注与支持！<br><span style='float: right;'>志愿墙技术团队<br>{$DATATIME}</span></p><br><br></div><p style='padding: 0;margin: 0;line-height: 30px;'>&nbsp;</p></td><td style='width: 80px;background: #fff;'>&nbsp;</td></tr><tr><td colspan='3' style='color: #bbbbbb;font-size: 12px;padding: 5px 0;text-align: center;border-bottom: 1px solid #dedede;font-family: 'Microsoft YaHei', '微软雅黑', 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB',  Arial, sans-serif;'><span style='display: block;'>© 2017-2018 vowall.com 版权所有 </span></td></tr><tr><td colspan='3' style='color: #bbbbbb;font-size: 12px;padding: 5px 0;text-align: center;font-family: 'Microsoft YaHei', '微软雅黑', 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB',  Arial, sans-serif;'><a href='http://vowall.com' target='_blank' style='color: #bbbbbb;font-size: 12px;text-decoration: none;'>功能体验</a> |  <a href='http://vowall.com' target='_blank' style='color: #bbbbbb;font-size: 12px;text-decoration: none;'>联系我们</a> | <a href='http://vowall.com' target='_blank' style='color: #bbbbbb;font-size: 12px;text-decoration: none;'>关于我们</a> </td></tr></tbody></table></div></td></tr></tbody></table>";
		
		content=content.replace("{$DATATIME}", dateTimeStr).replace("{$URL}", url);//
		
		//用户志愿墙用户你好欢迎加入志愿墙,你的验证码是
		Email em=new Email(toMail,"邮箱验证码测试", content);
		try {
			createMimeMessage(em);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
