package com.moong.notice.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public enum MemberType {

	 Member(0)
	,Admin(1);
	
	private final int type;
	
	public MemberType valueOf(int type) {
		switch (type) {
			case 0: return MemberType.Member;
			case 1: return MemberType.Admin;
			default: throw new RuntimeException("유효하지 않은 타입입니다.");
		}
	}
}
