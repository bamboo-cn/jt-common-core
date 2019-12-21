package com.bamboo.common.entity;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;


/**   
 * @Title: ScheduledTasks.java 
 * @Package com.xm.quartz.config 
 * @Description: 系统通用任务执行mode
 * @author bamboo  <a href="zjcjava@163.com?subject=hello,bamboo&body=Dear Bamboo:%0d%0a描述你的问题：">Bamboo</a>   
 * @date 2017年6月8日 上午10:15:56 
 * @version V1.0   
 */
public class SpringJobMode   implements Job {  
    private static final long serialVersionUID = 1L;
    
    public static String SRPTING_BEAN_NAME = "beanName";  
    
    public static String SRPTING_METHOD_NAME = "methodName"; 
    
    private static Logger logger = LoggerFactory.getLogger(SpringJobMode.class);
    
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	long start = System.currentTimeMillis();
    	JobDataMap data = context.getJobDetail().getJobDataMap();  
        try {  
            invokeMethod(data.getString(SRPTING_BEAN_NAME),data.getString(SRPTING_METHOD_NAME), null);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        long end = System.currentTimeMillis();
        double total=(end-start)/1000;
		System.out.println("OrderCloseJob end,耗时："+total+"s");
        //System.out.println ("Scheduling Tasks Examples By Cron: The time is now " + dateFormat ().format (new Date ()));
    }
    
    
    public void invokeMethod(String beanName, String methodName, Object[] args)  
            throws Exception {  
    	//Object ownerClass=SpringContextHolder.getBean(beanName);
        Object ownerClass = ContextLoader.getCurrentWebApplicationContext().getBean(beanName);  
        Method method = ownerClass.getClass().getMethod(methodName, null);  
        method.invoke(ownerClass, args); 
    }  

    private String dateFormat(){
        return new SimpleDateFormat ("HH:mm:ss").format (new Date ());
    }
    
}

