package com.moong.notice.api.advice.exception;


public class UserNotFoundException extends RuntimeException{
	
	private final static String MESSAGE_FORMAT = "%s는 존재하지 않는 유저입니다.";
	
	public UserNotFoundException(String uId) {
		super(String.format(MESSAGE_FORMAT, uId));
	}
}
