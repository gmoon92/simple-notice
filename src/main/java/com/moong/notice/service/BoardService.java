package com.moong.notice.service;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.moong.notice.domain.board.Board;
import com.moong.notice.domain.board.BoardType;
import com.moong.notice.repository.BoardRepository;

@Service
public class BoardService {
	private final BoardRepository boardRepository;
	private final int pageSize = 5;
	
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}
	
	//조회
	public Page<Board> findAll(final BoardType type
							  ,final Integer page
							  ,final String keyword){
		Pageable pageable = PageRequest.of(page
										 , pageSize
										 , new Sort(Direction.DESC, "id"));
		
		return boardRepository.findByTypeAndTitleLike(type, "%"+keyword+"%", pageable);
	}
	
	//저장
	public Board save(final Board board) {
		return boardRepository.save(board);
	}
	
	//수정
	public Integer update(Long id, @Valid Board board) {
		return boardRepository.updateBoard(id, board);
	}
	
	//삭제
	public void delete(Long id) {
		boardRepository.deleteById(id);
	}
	
}