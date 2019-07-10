package com.moong.notice.domain.board;

import com.moong.notice.api.advice.exception.BoardTypeNotFoundException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * 게시판을 구분할 enum
 * <li> 0 DEFAULT
 * <li> 1 NOTICE

 * @author moong
 **/
@Getter
@RequiredArgsConstructor
public enum BoardType {
	 DEFAULT(0) // 기본
	,NOTICE(1) // 공지사항
	;
	
	private final int type;

	/**
	 * <p>
	 * 요청된 Integer 타입의 type을 BoardType 타입으로 리턴
	 * @Note
	 * <pre>
	 * {@code
	 * BoardType type = BoardType.of(0);
	 * type <- BoardType.DEFAULT
	 * 
	 * // BoardTypeNotFoundException 예외 발생
	 * BoardType type = BoardType.of(3);
	 * }</pre>
	 * @author moong
	 * @return BoardType 타입의 enum
	 * @throws 정해진 타입의 값이 없을 경우 발생 
	 **/
	public static BoardType of(int type) {
		switch (type) {
			case 0: return BoardType.DEFAULT;
			case 1: return BoardType.NOTICE;
			default: throw new BoardTypeNotFoundException(type);
		}
	}
}
