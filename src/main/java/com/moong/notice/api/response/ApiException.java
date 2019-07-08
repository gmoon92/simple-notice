package com.moong.notice.api.response;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class ApiException <T> {

	private ApiResponseCode code;
	private String message;
	private T info;
	
	private ApiException(ApiResponseCode code
						,String message
						,T info) {
		this.code = code;
		this.message = message;
		this.info = info;
	}
	
	public static ApiException of(ApiResponseCode code) {
		return new ApiException(code, code.getMessage(), null);
	}
	
	public static ApiException of(ApiResponseCode code, String message) {
		return new ApiException(code, message, null);
	}
	
	public static <T> ApiException<T> withInfo(ApiResponseCode code, T info){
		return new ApiException<T>(code, null, info);
	}
	
	public static <T> ApiException<T> withInfo(ApiResponseCode code, String message, T info){
		return new ApiException<T>(code, message, info);
	}
}
