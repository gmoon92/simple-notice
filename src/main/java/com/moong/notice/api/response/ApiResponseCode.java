package com.moong.notice.api.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiResponseCode {

    OK("요청이 성공했습니다.")
   ,BAD_PARAMETER("잘못된 요청파라미터입니다.")
   ,NOT_FOUND("요청한 리소스를 찾을 수 없습니다.")
   ,FAILURE("요청이 실패했습니다.")
   ;
   private final String message;
   
}
