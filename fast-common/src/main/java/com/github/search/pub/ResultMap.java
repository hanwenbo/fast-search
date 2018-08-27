package com.github.search.pub;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Description: API接口返回包装类
 * User: zhubo
 * Date: 2017/6/19
 * Time: 下午 05:10
 */
public class ResultMap implements Serializable {
	private static final long serialVersionUID = -8673886735675934115L;

	private Integer code;
	private String msg;
	private Object data;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
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

	public ResultMap(){
		super();
	}

	public ResultMap(Integer errCode, String errMessage, Object data) {

		this.code = errCode;
		this.msg = errMessage;
		this.data = data;
	}

	public ResultMap(Integer errCode, String errMessage) {
		this.code = errCode;
		this.msg = errMessage;
		this.data = new HashMap<>();
	}
	public ResultMap(APICode apiCode){
		this.code = apiCode.getCode();
		this.msg = apiCode.getMsg();
	}

	public ResultMap(APICode apiCode, Object obj){
		this.code = apiCode.getCode();
		this.msg = apiCode.getMsg();
		this.data = obj;
	}

	public void setAPICode(APICode apiCode){
		this.code = apiCode.getCode();
		this.msg = apiCode.getMsg();
	}
}
