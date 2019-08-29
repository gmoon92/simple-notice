package com.moong.notice.service.dto;

import com.moong.notice.api.advice.exception.SelectOptionNotFoundException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

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

	public static SelectOptions of(final int option) {
		final SelectOptions[] options = SelectOptions.values();
		return Stream.of(options)
				.filter(o -> o.getOption() == option)
				.findFirst()
				.orElseThrow(() -> {
					throw new SelectOptionNotFoundException(option);
				});
	}

}
