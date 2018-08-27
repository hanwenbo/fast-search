package com.github.search.pub;

/**
 * Description: API ERROR_CODE_MESSAGE
 * User: zhubo
 * Date: 2017-06-18
 * Time: 10:40
 */
public enum APICode {

	OK(Integer.valueOf(0), "success"),
	RUNTIME_EXCEPTION(1, "系统异常！"),
	PARAM_INVALID(2, "参数错误！"),
	LOGIN_INVALID(3, "请先登录！"),
	PERMISSION_INVALID(4, "没有权限执行该操作！"),

	ES_CONNECT_ERROR(6001,"ES服务器链接异常"),
	ES_INSERT_ERROR(6002,"ES插入数据异常"),
	ES_UPDATE_ERROR(6003,"ES修改数据异常"),
	ES_DELETE_RRROR(6004,"ES删除数据异常"),
	ES_SELECT_RRROR(6005,"ES查询数据异常"),
	ES_CREATE_RRROR(6006,"ES创建索引异常"),
	ES_INIT_RRROR(6007,"ES初始化索引异常");



	private int code;
	private String msg;

	APICode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public static APICode getByCode(Integer code) {
		if (code == null) {
			return null;
		}
		for (APICode apiCode : APICode.values()) {
			if (apiCode.getCode() == code) {
				return apiCode;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return String.format("[code:%s, message:%s]", new Object[]{Integer.valueOf(this.code), this.msg});
	}
}
