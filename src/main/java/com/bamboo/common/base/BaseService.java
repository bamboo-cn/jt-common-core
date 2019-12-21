package com.bamboo.common.base;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bamboo.common.entity.Query;

/*******
 * 基础service类接口,所有业务基础服务基本参考此基础serve接口类型
 * @author zjcava@163.com
 *
 * @param <T>
 * @param <PK>
 */
public interface BaseService <T,PK extends Serializable>  {
	
	
	static Logger logger = LoggerFactory.getLogger(BaseService.class);
	
//	public ResultInfo resultInfo = new ResultInfo(Constant.SUCCESS,Constant.SUCCESS_MESSAGE,null);
//	/**
//	 * 获取单条数据
//	 * @param id
//	 * @return
//	 */
//	public ResultInfo get(PK id) ;
//
//
//	/**
//	 * 查询列表数据
//	 * @param entity
//	 * @return
//	 */
//	public ResultInfo getList(T entity) ;
//
//	/**
//	 * 查询分页数据
//	 * @param q 分页对象
//	 * @return
//	 */
//	public ResultInfo getPage(Query q) ;
//
//	/**
//	 * 保存数据（插入或更新）
//	 * @param entity
//	 * @return
//	 */
//	public ResultInfo saveOrUpdate(T entity) ;
//
//	/**
//	 * 删除数据
//	 * @param id
//	 * @return
//	 */
//	public ResultInfo delete(PK id) ;
	
	
}
