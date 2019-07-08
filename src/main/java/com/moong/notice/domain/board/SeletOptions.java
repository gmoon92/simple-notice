package com.moong.notice.domain.board;

import com.moong.notice.api.advice.exception.SelectOptionNotFoundException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SeletOptions {
	 TITLE(1)
	,CREATED_DATE(2)
	,WRITER(3)
	,MODIFED_DATE(4)
	,CONTENTS(5)
	;
	
	private final int option;
	
	public static SeletOptions of(int option) {
		switch (option) {
			case 1: return SeletOptions.TITLE;
			case 2: return SeletOptions.CREATED_DATE;
			case 3: return SeletOptions.WRITER;
			case 4: return SeletOptions.MODIFED_DATE;
			case 5: return SeletOptions.CONTENTS;
			default: throw new SelectOptionNotFoundException(option);
		}
	}
}
