package com.moong.notice.api.response;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class ApiResponse<T> {

	private ApiResponseCode code;
	private String message;
	private T data;
	
	public ApiResponse(ApiResponseCode code
					  ,String message
					  ,T data
			) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public static <T> ApiResponse<T> ok(T data){
		return ApiResponse.of(ApiResponseCode.OK, data);
	}

	public static <T> ApiResponse<T> of(ApiResponseCode code, T data){
		return new ApiResponse<T>(code, code.getMessage(), data);
	}
}
