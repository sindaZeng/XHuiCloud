
package com.zsinda.fdp.utils;

import com.zsinda.fdp.content.CommonContent;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class R<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 200成功
	 */
	@Getter
	@Setter
	private int code;

	@Getter
	@Setter
	private String msg;


	@Getter
	@Setter
	private T data;

	public static <T> R<T> ok() {
		return restResult(null, CommonContent.REQUEST_SUCCESS, null);
	}

	public static <T> R<T> ok(T data) {
		return restResult(data, CommonContent.REQUEST_SUCCESS, null);
	}

	public static <T> R<T> ok(T data, String msg) {
		return restResult(data, CommonContent.REQUEST_SUCCESS, msg);
	}

	public static <T> R<T> failed() {
		return restResult(null, CommonContent.REQUEST_FAIL, null);
	}

	public static <T> R<T> failed(String msg) {
		return restResult(null, CommonContent.REQUEST_FAIL, msg);
	}

	public static <T> R<T> failed(T data) {
		return restResult(data, CommonContent.REQUEST_FAIL, null);
	}

	public static <T> R<T> failed(T data, String msg) {
		return restResult(data, CommonContent.REQUEST_FAIL, msg);
	}

	private static <T> R<T> restResult(T data, int code, String msg) {
		R<T> apiResult = new R<>();
		apiResult.setCode(code);
		apiResult.setData(data);
		apiResult.setMsg(msg);
		return apiResult;
	}
}
