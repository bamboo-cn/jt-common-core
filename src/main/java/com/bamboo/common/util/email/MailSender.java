package com.bamboo.common.util.email;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 邮件（不带附件的邮件）发送器
 */
public class MailSender {

    public static String REAL_PATH = null;

    public static MyAuthenticator athenticator(MailSenderInfo mailInfo) throws Exception{

        MyAuthenticator authenticator=null;
        Properties pro = mailInfo.getProperties();
        // 判断是否需要身份认证
        if (mailInfo.isValidate()) {
            // 如果需要身份认证，则创建一个密码验证器
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }

        // 是否开启SSL加密，否则会失败
        if(mailInfo.isSslEnable()){
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            pro.put("mail.smtp.ssl.enable", "true");
            pro.put("mail.smtp.ssl.socketFactory", sf);
            mailInfo.setProperties(pro);
        }

        return  authenticator;
    }



    /**
     * 以文本格式发送邮件
     * @param mailInfo 待发送的邮件的信息
     */
    public static void sendTextMail(MailSenderInfo mailInfo) throws Exception{
        // 判断是否需要身份认证
        MyAuthenticator authenticator = athenticator(mailInfo);
        //Properties pro = mailInfo.getProperties();


        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(mailInfo.getProperties(),authenticator);

        // 根据session创建一个邮件消息
        Message mailMessage = new MimeMessage(sendMailSession);
        // 创建邮件发送者地址
        Address from = new InternetAddress(mailInfo.getFromAddress());
        // 设置邮件消息的发送者
        mailMessage.setFrom(from);
        // 创建邮件的接收者地址，并设置到邮件消息中
        Address to = new InternetAddress(mailInfo.getToAddress());
        mailMessage.setRecipient(Message.RecipientType.TO,to);
        // 设置邮件消息的主题
        mailMessage.setSubject(mailInfo.getSubject());
        // 设置邮件消息发送的时间
        mailMessage.setSentDate(new Date());
        // 设置邮件消息的主要内容
        String mailContent = mailInfo.getContent();
        mailMessage.setText(mailContent);
        // 发送邮件
        Transport.send(mailMessage);


    }

    /**
     * 以HTML格式发送邮件
     * @param mailInfo 待发送的邮件信息
     */
    public static void sendHtmlMail(MailSenderInfo mailInfo)  throws Exception{
        // 判断是否需要身份认证
        MyAuthenticator authenticator =athenticator(mailInfo);
       // athenticator(mailInfo,authenticator);
        Properties pro =  mailInfo.getProperties();

        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(mailInfo.getProperties(),authenticator);

        // 根据session创建一个邮件消息
        Message mailMessage = new MimeMessage(sendMailSession);
        // 创建邮件发送者地址
        Address from = new InternetAddress(mailInfo.getFromAddress());
        // 设置邮件消息的发送者
        mailMessage.setFrom(from);
        // 创建邮件的接收者地址，并设置到邮件消息中
        String [] atrArry=mailInfo.getToAddress().split(",");
        Address[] toAddress= new Address[atrArry.length];
        for (int i=0 ; i<atrArry.length ; i++){
            Address to = new InternetAddress(atrArry[i]);
            toAddress[i] = to;
        }
        // Message.RecipientType.TO属性表示接收者的类型为TO
        mailMessage.setRecipients(Message.RecipientType.TO,toAddress);
        // 设置邮件消息的主题
        mailMessage.setSubject(mailInfo.getSubject());
        // 设置邮件消息发送的时间
        mailMessage.setSentDate(new Date());
        // MiniMultipart类是一个容器类， // Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象
        Multipart mainPart = new MimeMultipart();
        // 创建一个包含HTML内容的MimeBodyPart
        BodyPart html = new MimeBodyPart();
        // 设置HTML内容
        html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
        mainPart.addBodyPart(html);


        // 设置附件
        List<AttachFile> fileList = mailInfo.getAttachFileList();
        if (fileList != null && fileList.size() > 0) {
            for (int i = 0; i < fileList.size(); i++) {
                MimeBodyPart bp = new MimeBodyPart();
                if(fileList.get(i).url.indexOf("http")>0){
                    URLDataSource  ur= new URLDataSource(new URL(fileList.get(i).url));
                    bp.setDataHandler(new DataHandler(ur));
                }else {
                    FileDataSource fds = new FileDataSource( REAL_PATH + File.separator+ fileList.get(i).url);
                    bp.setDataHandler(new DataHandler(fds));
                }

                bp.setFileName(MimeUtility.encodeText(fileList.get(i).name, "UTF-8", "B"));
                mainPart.addBodyPart(bp);
            }
        }
        // 将MiniMultipart对象设置为邮件内容
        mailMessage.setContent(mainPart);
        mailMessage.saveChanges();

        // 发送邮件
        Transport.send(mailMessage);


    }



    public static void main(String[] args)throws Exception{
        //这个类主要是设置邮件
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost("smtp.mxhichina.com");
        mailInfo.setMailServerPort("465");
        mailInfo.setValidate(true);
        mailInfo.setSslEnable(true);
        mailInfo.setUserName("info@beske.com");
        mailInfo.setPassword("Beske1508");//您的邮箱密码
        mailInfo.setFromAddress("info@beske.com");
        mailInfo.setToAddress("zjcjava@163.com,348285745@qq.com");
        mailInfo.setSubject("设置邮箱标题");
        mailInfo.setContent("设置邮箱内容");
        String []f ={"http://res.yunzhzhu.com/yzz/1/undefined/QW12INCAQBEiVcJLOkETTsaI4aHXQ82r_undefined_510*160.jpg"};
        mailInfo.setAttachFileNames(f);
        //这个类主要来发送邮件
        MailSender sms = new MailSender();
       // sms.sendTextMail(mailInfo);//发送文体格式
        sms.sendHtmlMail(mailInfo);//发送html格式
    }

}