/**
 * 
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bamboo.common.entity;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;


/**
 * 
 * 系统日志Entity
 * @author ThinkGem
 * @version 2014-8-19
 */
public class SysLog   {

	private static final long serialVersionUID = 1L;
	private String id; 
	private String sysName;		//系统名称
	//private String modeName; 	// 模块名称
	private int type; 		// 日志类型（0：接入日志；1：错误日志）
	private String description;		// 描述信息
	private String ip; 	// 操作用户的IP地址
	private String requestUri; 	// 操作的URI
	private String method; 		// 操作的方式
	private String params; 		// 操作提交的数据
	private String userAgent;	// 操作用户代理信息
	private String excode; 	// 异常信息code
	private String exception; 	// 异常信息
	private double executeTime; 	// 运行耗时
	private String isSystem; 	// 运行耗时:是否是管理系统1是,0不是(其他功能子系统)
	
	

	private Date beginDate;		// 开始日期
	private Date endDate;		// 结束日期
	
	
//	protected String remarks;	// 备注
	protected String createBy;	// 创建者
	protected Date createDate;	// 创建日期
//	protected String updateBy;	// 更新者
//	protected Date updateDate;	// 更新日期
//	protected String delFlag; 	// 删除标记（0：正常；1：删除；2：审核）
	
	// 日志类型（0：接入日志；1：错误日志）
	public static final int TYPE_ACCESS = 0;
	public static final int TYPE_EXCEPTION = 1;
	
	public SysLog(){
		super();
	}
	
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	
 

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public String getExcode() {
		return excode;
	}


	public void setExcode(String excode) {
		this.excode = excode;
	}


	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
	
	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	
	
	
	public String getCreateBy() {
		return createBy;
	}


	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	

	public String getSysName() {
		return sysName;
	}


	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	

	public double getExecuteTime() {
		return executeTime;
	}


	public void setExecuteTime(double executeTime) {
		this.executeTime = executeTime;
	}

	
	
	
	public String getIsSystem() {
		return isSystem;
	}


	public void setIsSystem(String isSystem) {
		this.isSystem = isSystem;
	}


	/**
	 * 设置请求参数
	 * @param paramMap
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setParams(Map paramMap){
		if (paramMap == null){
			return;
		}
		StringBuilder params = new StringBuilder();
		for (Map.Entry<String, String[]> param : ((Map<String, String[]>)paramMap).entrySet()){
			params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
			String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
			//params.append(StringUtils.abbr(StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue, 100));
			params.append(paramValue);
		}
		this.params = params.toString();
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}


	


	
}