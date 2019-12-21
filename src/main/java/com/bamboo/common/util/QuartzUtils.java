package com.bamboo.common.util;


import com.bamboo.common.entity.SpringJobMode;
import com.bamboo.common.entity.SysJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * 类名：QuartzManager <br/>
 * 功能：<br/>
 * 详细：Quartz增、删、改工具类 <br/>
 * 作者： Tliu <br/>
 * 日期：2015-7-17 <br/>
 */
public class QuartzUtils {
 
	private static Logger log = LoggerFactory.getLogger(PropertiesLoader.class);
 
	private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
     
    public static final String DATA_KEY = "STATE_DATA";
     
    
    
    /**
	 * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 *
	 * @param jobName
	 *            任务名
	 * @param jobClass
	 *            任务
	 * @param time
	 *            时间设置，参考quartz说明文档
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public static void addOrUpdateJob(SysJob sysJob) {
		
		
		if(checkExists(sysJob.getJobName(), sysJob.getJobGroup())){
			modifyTime(sysJob);
		}else{
			try {
				Scheduler scheduler = gSchedulerFactory.getScheduler();
				//JobDetail jobDetail = new JobDetail (jobName, JOB_GROUP_NAME, Class.forName(jobClass));// 任务名，任务组，任务执行类
				@SuppressWarnings("unchecked")
				JobDetail jobDetail = JobBuilder.newJob(SpringJobMode.class)
		                .withIdentity(sysJob.getJobName(), sysJob.getJobGroup()).build();
		        jobDetail.getJobDataMap().put(SpringJobMode.SRPTING_BEAN_NAME, sysJob.getBeanName());
				jobDetail.getJobDataMap().put(SpringJobMode.SRPTING_METHOD_NAME, sysJob.getMethodName());
		        
		        // 表达式调度构建器
		        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(sysJob.getCronExpression())
		                .withMisfireHandlingInstructionDoNothing();
		        // 构建一个trigger
		        TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger()//.withIdentity(triggerKey)
		                .withSchedule(scheduleBuilder);
		        if (sysJob.getStartTime() != null) {
		            triggerBuilder.startAt(sysJob.getStartTime());
		        }
		        if (sysJob.getEndTime() != null) {
		            triggerBuilder.endAt(sysJob.getEndTime());
		        }
		        CronTrigger trigger = triggerBuilder.build();
		        
		        //构造任务触发器
	           /* Trigger trigger = newTrigger()
	                    .withIdentity(job.getJobName(), job.getJobGroup())
	                    .withSchedule(cronSchedule(job.getCronExpression()))
	                    .build();*/    
		        
		        scheduler.scheduleJob(jobDetail, trigger);// 注入到管理类
		        
				// 启动
				if (!scheduler.isShutdown()){
					scheduler.start();
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		
	}
    
    
     //删除任务
    public static void removeJob(String name, String group){
        try {
        	Scheduler scheduler = gSchedulerFactory.getScheduler();
            TriggerKey tk = TriggerKey.triggerKey(name, group);
            scheduler.pauseTrigger(tk);//停止触发器  
            scheduler.unscheduleJob(tk);//移除触发器
            JobKey jobKey = JobKey.jobKey(name, group);
            scheduler.deleteJob(jobKey);//删除作业
            log.info("删除作业=> [作业名称：" + name + " 作业组：" + group + "] ");
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error("删除作业=> [作业名称：" + name + " 作业组：" + group + "]=> [失败]");
        }
    }
     
    
    //暂停作业
    public static void pauseJob(String name, String group){
        try {
        	Scheduler scheduler = gSchedulerFactory.getScheduler();
            JobKey jobKey = JobKey.jobKey(name, group);
            scheduler.pauseJob(jobKey);
            log.info("暂停作业=> [作业名称：" + name + " 作业组：" + group + "] ");
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error("暂停作业=> [作业名称：" + name + " 作业组：" + group + "]=> [失败]");
        }
    }
     
  //恢复作业
    public static void resumeJob(String name, String group){
        try {
        	Scheduler scheduler = gSchedulerFactory.getScheduler();
            JobKey jobKey = JobKey.jobKey(name, group);         
            scheduler.resumeJob(jobKey);
            log.info("恢复作业=> [作业名称：" + name + " 作业组：" + group + "] ");
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error("恢复作业=> [作业名称：" + name + " 作业组：" + group + "]=> [失败]");
        }       
    }
     
    
    //修改任务:该方法作废
    public static void modifyTime(SysJob sysJob) {
		
		
		if(checkExists(sysJob.getJobName(), sysJob.getJobGroup())){
			try {
	        	Scheduler scheduler = gSchedulerFactory.getScheduler();
	            TriggerKey tk = TriggerKey.triggerKey(sysJob.getJobName(), sysJob.getJobGroup());
	            //构造任务触发器
	            Trigger trigger = newTrigger()
	                    .withIdentity(sysJob.getJobName(), sysJob.getJobGroup())
	                    .withSchedule(cronSchedule(sysJob.getCronExpression()))
	                    .build();
	            
	        /* // 表达式调度构建器
		        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression)
		                .withMisfireHandlingInstructionDoNothing();
	         // 构建一个trigger
		        TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger()
		                .withSchedule(scheduleBuilder);
		        if (startTime != null) {
		            triggerBuilder.startAt(startTime);
		        }
		        if (endTime != null) {
		            triggerBuilder.endAt(endTime);
		        }
		        CronTrigger trigger = triggerBuilder.build();*/
	            scheduler.rescheduleJob(tk, trigger);
	            log.info("修改作业触发时间=> [作业名称：" + sysJob.getJobName() + " 作业组：" + sysJob.getJobGroup() + "] ");
	        } catch (SchedulerException e) {
	            e.printStackTrace();
	            log.error("修改作业触发时间=> [作业名称：" + sysJob.getJobName() + " 作业组：" + sysJob.getJobGroup() + "]=> [失败]");
	        }
		}
        
    }
 
    public static void start() {
        try {
        	Scheduler scheduler = gSchedulerFactory.getScheduler();
            scheduler.start();
            log.info("启动调度器 ");
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error("启动调度器=> [失败]");
        }
    }
 
    public static void shutdown() {
        try {
        	Scheduler scheduler = gSchedulerFactory.getScheduler();
            scheduler.shutdown();
            log.info("停止调度器 ");
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error("停止调度器=> [失败]");
        }
    }
    
    /******
     * 检查job是否存在，存在则返回true
     * @param name
     * @param group
     * @return
     */
    public static boolean checkExists(String name, String group){
    	boolean flag=false;
    	try {
			Scheduler scheduler = gSchedulerFactory.getScheduler();
			TriggerKey tk = TriggerKey.triggerKey(name, group);
			flag=scheduler.checkExists(tk);
			log.error("检查任务情况");
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("检查任务情况=> [失败]");
		}
    	return flag;
    }
    
    
    
    
	
	public static void main(String[] args) throws SchedulerException {
        //Scheduler scheduler = gSchedulerFactory.getScheduler();
		SysJob job=new SysJob();
        job.setCronExpression("0/1 * * * * ?");//每1秒执行一次0 0/5 * * * ?
        job.setJobGroup("tst");
        job.setJobName("helll");
        job.setStartTime(new Date());
        //job.setEndTime(new Date(System.currentTimeMillis() +10* 1000));
        
        addOrUpdateJob(job);
        
        try {
			Thread.sleep(10000l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        removeJob(job.getJobName(), job.getJobGroup());
        
        //job.setEndTime(new Date(System.currentTimeMillis() +10* 1000));
//        job.setCronExpression("* 0/1 * * * ?");//每1秒执行一次0 0/5 * * * ?
//        modifyTime(job.getJobName(), job.getJobGroup(), job.getCronExpression());
       
	}
    
    
}