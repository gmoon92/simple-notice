package com.moong.notice.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public enum MemberRules {
	 MEMBER("USER")
	,ADMIN("ADMIN")
	;
	
	private final String rule;
}
