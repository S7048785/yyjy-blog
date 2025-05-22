package com.yyjy.result;

import lombok.Data;

@Data
public class Result<T> {
	private Integer code;
	private String msg;
	private T data;

	public static <T> Result<T> ok(T data) {
		Result<T> result = new Result<T>();
		result.setData(data);
		result.setCode(1);
		result.setMsg("ok");
		return result;
	}
	public static <T> Result<T> ok() {
		Result<T> result = new Result<T>();
		result.setCode(1);
		result.setMsg("ok");
		return result;
	}
	public static <T> Result<T> error(String msg) {
		Result<T> result = new Result<T>();
		result.setCode(0);
		result.setMsg(msg);
		return result;
	}
}
