package com.moong.notice.service.dto;

import com.moong.notice.api.advice.exception.SelectOptionNotFoundException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SelectOptions {
	 TITLE(1)
	,CREATED_DATE(2)
	,WRITER(3)
	,MODIFED_DATE(4)
	,CONTENTS(5)
	;
	
	private final int option;
	
	public static SelectOptions of(int option) {
		switch (option) {
			case 1: return SelectOptions.TITLE;
			case 2: return SelectOptions.CREATED_DATE;
			case 3: return SelectOptions.WRITER;
			case 4: return SelectOptions.MODIFED_DATE;
			case 5: return SelectOptions.CONTENTS;
			default: throw new SelectOptionNotFoundException(option);
		}
	}
}
