package com.bamboo.common.entity;


import com.bamboo.common.constant.Constant;

import java.io.Serializable;
import java.util.List;



/**
 * 分页对象. 包含当前页数据及分页信息
 * 
 */
@SuppressWarnings("serial")
public class PageFinder<T> implements Serializable {

	/**
	 * 每页的记录数
	 */
	private long pageSize = Constant.DEFAULT_PAGE_SIZE;

	/**
	 * 当前页中存放的数据
	 */
	private List<T> data;

	/**
	 * 总记录数
	 */
	private long rowCount;

	/**
	 * 总页数
	 */
	private long pageCount;

	/**
	 * 当前第几页
	 */
	private long pageNo;

	/**
	 * 是否有上一页
	 */
	private boolean hasPrevious = false;

	/**
	 * 是否有下一页
	 */
	private boolean hasNext = false;
	
	public PageFinder() {
		refresh();
	}

	public PageFinder(long pageNo, long rowCount) {
		this.pageNo = pageNo;
		this.rowCount = rowCount;
		this.pageCount = getTotalPageCount();
		refresh();
	}

	/**
	 * 构造方法
	 */
	public PageFinder(long pageNo, long pageSize, long rowCount) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.rowCount = rowCount;
		this.pageCount = getTotalPageCount();
		refresh();
	}

	/**
	 * 
	 */
	public PageFinder(long pageNo, long pageSize, long rowCount, List<T> data) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.rowCount = rowCount;
		this.pageCount = getTotalPageCount();
		this.data = data;
		refresh();
	}

	/**
	 * 取总页数
	 */
	private final long getTotalPageCount() {
		if (rowCount % pageSize == 0)
			return rowCount / pageSize;
		else
			return rowCount / pageSize + 1;
	}

	/**
	 * 刷新当前分页对象数据
	 */
	private void refresh() {
		if (pageCount <= 1) {
			hasPrevious = false;
			hasNext = false;
		} else if (pageNo == 1) {
			hasPrevious = false;
			hasNext = true;
		} else if (pageNo == pageCount) {
			hasPrevious = true;
			hasNext = false;
		} else {
			hasPrevious = true;
			hasNext = true;
		}
	}

	/**
	 * 取每页数据数
	 */
	public long getPageSize() {
		return pageSize;
	}

	/**
	 * 取当前页中的记录.
	 */
	/*public Object getResult() {
		return data;
	}*/

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public long getRowCount() {
		return rowCount;
	}

	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
	}

	public long getPageCount() {
		return pageCount;
	}

	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}

	public long getPageNo() {
		return pageNo;
	}

	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}

	public boolean isHasPrevious() {
		return hasPrevious;
	}

	public void setHasPrevious(boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获取跳转页第一条数据在数据集的位置
	 */
	public long getStartOfPage() {
		return (pageNo - 1) * pageSize;
	}
	public void setPageParam(Query query,long rowCount) {
		this.setRowCount(rowCount);
		this.setPageNo(query.getPageNo());
		this.setPageSize(query.getPageSize());
		long pageCount = getTotalPageCount();
		this.setPageCount(pageCount);
		refresh();
	}
}
