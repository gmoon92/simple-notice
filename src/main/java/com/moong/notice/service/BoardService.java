package com.moong.notice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moong.notice.aspect.BoardValidation;
import com.moong.notice.domain.board.Board;
import com.moong.notice.domain.member.Member;
import com.moong.notice.domain.member.MemberRules;
import com.moong.notice.repository.BoardRepository;
import com.moong.notice.repository.MemberRepository;
import com.moong.notice.service.dto.BoardParam;
import com.moong.notice.service.dto.SearchParam;
import com.moong.notice.service.dto.SelectOptions;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	
	//조회
	public Page<Board> findAll(final Integer page
							 , final SearchParam params){
		Pageable pageable = PageRequest.of(page-1 // view에서 페이지 번호로 직접 넘겨줌, jpa 시작은 0 부터
										 , 5 // page block limit
										 , new Sort(Direction.DESC, "id"));
		
		return boardRepository.findSearchQuery(params, pageable);
		/*
		SelectOptions oDate    = params.getOption_date();
		SelectOptions oKeyword = params.getOption_keyword();
		switch (oDate) {
			case CREATED_DATE:
				switch (oKeyword) {
					case CONTENTS: return boardRepository
								.findByTypeAndContentsContainsAndCreatedDateBetween( params.getType()
																					,params.getKeyword()
																					,params.getSta_ymd()
																					,params.getEnd_ymd()
																					,pageable);
					case TITLE:
						return boardRepository
								.findByTypeAndTitleContainsAndCreatedDateBetween( params.getType()
																				 ,params.getKeyword()
																				 ,params.getSta_ymd()
																				 ,params.getEnd_ymd()
																				 ,pageable);
					case WRITER:
						return boardRepository
								.findByTypeAndWriterCreatedDateBetweenQuery( params.getType().name()
																			,params.getKeyword()
																			,params.getSta_ymd()
																			,params.getEnd_ymd()
																			,pageable);
				}
					
			break;
			case MODIFED_DATE:
				switch (oKeyword) {
					case CONTENTS: return boardRepository
								.findByTypeAndContentsContainsAndModifiedDateBetween( params.getType()
																					,params.getKeyword()
																					,params.getSta_ymd()
																					,params.getEnd_ymd()
																					,pageable);
					case TITLE:
						return boardRepository
								.findByTypeAndTitleContainsAndModifiedDateBetween( params.getType()
																				 ,params.getKeyword()
																				 ,params.getSta_ymd()
																				 ,params.getEnd_ymd()
																				 ,pageable);
					case WRITER:
						return boardRepository
								.findByTypeAndWriterModifiedDateBetweenQuery( params.getType().name()
																			,params.getKeyword()
																			,params.getSta_ymd()
																			,params.getEnd_ymd()
																			,pageable);
				}
			break;
		}
		return null;
		*/
	}
	
	//저장
	@BoardValidation
	@Transactional
	public Board save(final BoardParam boardPrams) {
		return boardRepository.save(boardPrams.toEntity());
	}
	
	//수정
	@BoardValidation
	@Transactional
	public Integer update(final Long id, final BoardParam boardPrams) {
		return boardRepository.updateBoard(id, boardPrams.toEntity());
	}
	
	//삭제
	@BoardValidation(value=MemberRules.ADMIN)
	@Transactional
	public void delete(final Long id) {
		boardRepository.deleteById(id);
	}
	
}