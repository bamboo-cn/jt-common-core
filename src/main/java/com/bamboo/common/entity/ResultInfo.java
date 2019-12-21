package com.xialeme.common.core.result;

/**
 * 返回�?
 *
 * Created by bamoo on 14/05/2017.
 */

public class ResultInfo {
	/**
	 * 响应代码
	 */
	private int code;

	/**
	 * 响应消息
	 */
	private String msg;// 消息
	/**
	 * 响应结果
	 */
	private Object data;// 数据


	public ResultInfo() {
		super();
	}

	/*public ResultInfo(ErrorInfoInterface errorInfo) {
		this.code = errorInfo.getCode();
		this.msg = errorInfo.getMessage();
	}

	public ResultInfo(Object result) {
		this.code = GlobalErrorInfoEnum.SUCCESS.getCode();
		this.msg = GlobalErrorInfoEnum.SUCCESS.getMessage();
		this.data = result;
	}*/

	public ResultInfo(int code, String msg,Object  data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResultInfo [code=" + code + ", msg=" + msg + ", data=" + data
				+ "]";
	}


}
