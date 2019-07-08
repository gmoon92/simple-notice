package com.moong.notice.api.advice.exception;

import lombok.NoArgsConstructor;

/**
 * 예외를 구분하기 위한 목적으로 생성
 * @author moong
 **/
@NoArgsConstructor
public class SelectOptionNotFoundException extends RuntimeException{

	private final static String MESSAGE_FORMAT = "%d는 존재하지 않는 옵션입니다.";
	
	public SelectOptionNotFoundException(int type) {
		super(String.format(MESSAGE_FORMAT, type));
	}
	
}
