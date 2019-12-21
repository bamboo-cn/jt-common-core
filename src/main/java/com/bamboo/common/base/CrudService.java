/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bamboo.common.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bamboo.common.entity.PageFinder;
import com.bamboo.common.entity.Query;

/**
 * Service基类
 * @author ThinkGem
 * @version 2014-05-16
 */
//@Transactional(readOnly = true)
public abstract class CrudService {

/*
<D extends BaseDao<T,PK>, T ,PK extends Serializable> extends BaseService1 {
	
	*//**
	 * 持久层对象
	 *//*
	@Autowired(required=true)
	protected D dao;
	
	*//**
	 * 获取单条数据
	 * @param id
	 * @return
	 *//*
	public T get(PK id) {
		return dao.get(id);
	}
	
	*//**
	 * 获取单条数据
	 * @param entity
	 * @return
	 *//*
	public T get(T entity) {
		return dao.get(entity);
	}
	
	*//**
	 * 查询列表数据
	 * @param entity
	 * @return
	 *//*
	public List<T> getList(T entity) {
		return dao.getList(entity);
	}
	
	*//**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 *//*
	public PageFinder<T> getPage(Query q) {
		
		PageFinder<T> page = new PageFinder<T>();

		List<T> list = null;
		long rowCount = 0L;
		// 调用dao查询满足条件的分页数据
		list = dao.pageList(q);
		// 调用dao统计满足条件的记录总数
		rowCount = dao.count(q);
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<T>(0) : list;
		//list = (List<T>) commonService.setOperatorInfoOfEntityList(list);

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}

	*//**
	 * 保存数据（插入或更新）
	 * @param entity
	 *//*
	@Transactional(readOnly = false)
	public void saveOrUpdate(T entity) {
		if (entity.getIsNewRecord()){
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}
	
	*//**
	 * 删除数据
	 * @param entity
	 *//*
	@Transactional(readOnly = false)
	public void delete(PK id) {
		dao.delete(id);
	}*/

}
