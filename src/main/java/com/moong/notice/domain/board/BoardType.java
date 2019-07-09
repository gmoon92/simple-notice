package com.moong.notice.domain.board;

import com.moong.notice.api.advice.exception.BoardTypeNotFoundException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 게시판을 구분할 enum
 * @author moong
 **/
@Getter
@RequiredArgsConstructor
public enum BoardType {
	 DEFAULT(0) // 기본
	,NOTICE(1) // 공지사항
	;
	
	private final int type;

	public static BoardType of(int type) {
		switch (type) {
			case 0: return BoardType.DEFAULT;
			case 1: return BoardType.NOTICE;
			default: throw new BoardTypeNotFoundException(type);
		}
	}
}
