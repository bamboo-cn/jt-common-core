package com.bamboo.common.entity;
import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;



/**
 * @Title: SysJob.java 
 * @Package com.xialeme.modules.apply.entity
 * @Description: 定时任务job对象
 * @author bamboo  <a href=
 *         "mailto:zjcjava@163.com?subject=hello,bamboo&body=Dear Bamboo:%0d%0a描述你的问题："
 *         Bamboo</a>   
 * @date 2017-6-21 16:39:44
 * @version V1.0   
 */
public class SysJob  implements Serializable {
	/** serialVersionUID. */
	private static final long serialVersionUID =1493049839167L;

	private String id;//日志ID编号
	private String jobName;//定时器的Name
	private String jobGroup;//任务组

	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date startTime;//开始时间
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date endTime;//结束时间
	private String cronExpression;//cron表达式
	private String beanName;//执行的类名
	private String methodName;//执行的方法名
	private String remark;//备注
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date createTime;//创建时间
	private Integer state;//运行状态：0删除、1停止2、运行


	/**
	 * getting setting auto  generate
	 */
	public void setId (String id){
		this.id=id;
	}

	public String getId(){
		return id;
	}



	public void setJobName (String jobName){
		this.jobName=jobName;
	}

	public String getJobName(){
		return jobName;
	}



	public void setJobGroup (String jobGroup){
		this.jobGroup=jobGroup;
	}

	public String getJobGroup(){
		return jobGroup;
	}



	public void setStartTime (Date startTime){
		this.startTime=startTime;
	}

	public Date getStartTime(){
		return startTime;
	}



	public void setEndTime (Date endTime){
		this.endTime=endTime;
	}

	public Date getEndTime(){
		return endTime;
	}



	public void setCronExpression (String cronExpression){
		this.cronExpression=cronExpression;
	}

	public String getCronExpression(){
		return cronExpression;
	}



	public void setBeanName (String beanName){
		this.beanName=beanName;
	}

	public String getBeanName(){
		return beanName;
	}



	public void setMethodName (String methodName){
		this.methodName=methodName;
	}

	public String getMethodName(){
		return methodName;
	}



	public void setRemark (String remark){
		this.remark=remark;
	}

	public String getRemark(){
		return remark;
	}



	public void setCreateTime (Date createTime){
		this.createTime=createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}



	public void setState (Integer state){
		this.state=state;
	}

	public Integer getState(){
		return state;
	}



	//generate toString method
	@Override
	public String toString (){
		return "SysJob[id="+id
				+",jobName="+jobName
				+",jobGroup="+jobGroup
				+",startTime="+startTime
				+",endTime="+endTime
				+",cronExpression="+cronExpression
				+",beanName="+beanName
				+",methodName="+methodName
				+",remark="+remark
				+",createTime="+createTime
				+",state="+state+"]";
	}


}
