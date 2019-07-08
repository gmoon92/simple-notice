package com.moong.notice.api.response;

import lombok.Getter;
import lombok.ToString;

/**
 * 결과 데이터 확장성을 고려한 
 * 결과 데이터의 구조를 표준화
 * @author moong
 **/
@Getter @ToString
public class ApiResponse<T> {

	private ApiResponseCode code;
	private String message;
	private T data;
	
	private ApiResponse(ApiResponseCode code
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
