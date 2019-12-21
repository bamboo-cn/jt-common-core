package com.bamboo.common.base;

import java.io.Serializable;


/**
 * 基础实体类
 * @author yong
 *
 * @param <PK>
 */
public abstract class Entity<PK extends Serializable> implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	//操作人类型（0、系统自动操作，1、平台人员操作，2、商家人员操作，3、会员操作）/////业务表一般都会有此字段
	private Integer operatorType;

	//操作人id（根据操作人类型会对应不同的表记录）/////业务表一般都会有此字段
	private Long operatorId;

	//通用操作人对象
	//private Operator operator;
	
	/**
	 * 删除标记（0：正常；1：删除；2：审核；）
	 */
	public static final String DEL_FLAG_NORMAL = "0";
	public static final String DEL_FLAG_DELETE = "1";
	public static final String DEL_FLAG_AUDIT = "2";
	

	
	/**
	 * 得到主键
	 * @return
	 */
	public abstract PK getPK();

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	

	
}
