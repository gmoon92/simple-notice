package com.moong.notice.service.dto;

import com.moong.notice.api.advice.exception.SelectOptionNotFoundException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <p>화면 Select 기준 값
 * <li> 1 TITLE
 * <li> 2 CREATE DATE
 * <li> 3 WRITER
 * <li> 4 MODIFED DATE
 * <li> 5.CONTETNS 
 * @author moong
 **/
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
