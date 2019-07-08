
package com.moong.notice.api.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.moong.notice.api.advice.exception.BoardTypeNotFoundException;
import com.moong.notice.api.advice.exception.SelectOptionNotFoundException;
import com.moong.notice.api.advice.exception.UserNotFoundException;
import com.moong.notice.api.response.ApiException;
import com.moong.notice.api.response.ApiResponseCode;

import lombok.extern.slf4j.Slf4j;

/**
 * API 호출 실패 처리
 * @author moong
 * */
@Slf4j
@RestControllerAdvice(annotations=RestController.class)
public class APICommonAdvice {

	@ExceptionHandler(BoardTypeNotFoundException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	protected ApiException<?> throwsTypeNotFound(BoardTypeNotFoundException e) {
		e.printStackTrace();
		ApiException exception = ApiException.of(ApiResponseCode.BAD_PARAMETER, e.getMessage());
		errorLogs(exception);
		return exception;
	}
	
	@ExceptionHandler(SelectOptionNotFoundException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	protected ApiException<?> throwSelectOptionNotFound(SelectOptionNotFoundException e) {
		e.printStackTrace();
		ApiException exception = ApiException.withInfo(ApiResponseCode.BAD_PARAMETER, "요청한 셀렉트 옵션 파라미터가 잘못 되었습니다.");
		errorLogs(exception);
		return exception;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	protected ApiException<?> throwsValidException(MethodArgumentNotValidException e){
		e.printStackTrace();
		ApiException exception = ApiException.of(ApiResponseCode.BAD_PARAMETER);
		errorLogs(exception);
		return exception;
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	protected ApiException<?> throwsValidException(UserNotFoundException e){
		e.printStackTrace();
		ApiException exception = ApiException.of(ApiResponseCode.NOT_FOUND, e.getMessage());
		errorLogs(exception);
		return exception;
	}
	
	
	
	private void errorLogs(ApiException<?> e) {
		log.error("[API][{}] {}", e.getCode(), e.getMessage());
	}
}
