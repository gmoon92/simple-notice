package com.moong.notice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.moong.notice.api.advice.exception.SelectOptionNotFoundException;
import com.moong.notice.domain.board.Board;
import com.moong.notice.domain.board.BoardType;
import com.moong.notice.domain.board.SeletOptions;
import com.moong.notice.repository.BoardRepository;
import com.moong.notice.repository.MemberRepository;
import com.moong.notice.service.dto.BoardParam;

@Service
public class BoardService {
	private final MemberRepository memberRepository;
	private final BoardRepository boardRepository;
	private final int pageSize = 5;
	
	public BoardService(BoardRepository boardRepository
					   ,MemberRepository memberRepository
						) {
		this.boardRepository = boardRepository;
		this.memberRepository = memberRepository;
	}
	
	//조회
	public Page<Board> findAll(final BoardType type
							  ,final Integer page
							  ,final String keyword
							  ,final Integer option){
		Pageable pageable = PageRequest.of(page
										 , pageSize
										 , new Sort(Direction.DESC, "id"));
		
		switch (SeletOptions.of(option)) {
			case TITLE			: return boardRepository.findByTypeAndTitleContains(type, keyword, pageable);
			case CONTENTS		: return boardRepository.findByTypeAndContentsContains(type, keyword, pageable);
			case WRITER			: return boardRepository.findByTypeAndWriterQuery(type.name(), keyword, pageable);
			case CREATED_DATE	: return boardRepository.findByTypeAndCreatedDateContains(type, keyword, pageable);
			case MODIFED_DATE	: return boardRepository.findByTypeAndCreatedDateContains(type, keyword, pageable);
		//	case TITLE: boardRepository.findByTypeAndTitleContains(type, keyword, pageable);
			
			default : throw new SelectOptionNotFoundException(option);
		}
	}
	
	//저장
	public Board save(final BoardParam boardPrams) {
		return boardRepository.save(boardPrams.toEntity());
	}
	
	//수정
	public Integer update(final Long id, final BoardParam boardPrams) {
		return boardRepository.updateBoard(id, boardPrams.toEntity());
	}
	
	//삭제
	public void delete(final Long id) {
		boardRepository.deleteById(id);
	}
	
}