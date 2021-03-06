package com.moong.notice.api.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moong.notice.api.response.ApiResponse;
import com.moong.notice.domain.board.Board;
import com.moong.notice.service.BoardService;
import com.moong.notice.service.dto.BoardParam;
import com.moong.notice.service.dto.SearchParam;

import lombok.RequiredArgsConstructor;

/**
 * 조회 GET
 * 작성 POST
 * 수정 PUT/PATCH
 * 삭제 DELETE
 * @author moong
 **/
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
	private final BoardService boardService;
	
	@GetMapping(value={"/{page}"})
	public ApiResponse< Page<Board> > getBoards(
			@PathVariable("page") Integer page
		   ,@Valid SearchParam params ){
		return ApiResponse.ok(boardService.findAll(page, params));
	}
	
	@PostMapping(path="/")
	public ApiResponse<Board> createBoard(@RequestBody @Valid BoardParam board ){
		return ApiResponse.ok(boardService.save(board));
	}
	
	//@PutMapping(path="/{id}") // <- 전체 리소스 바뀔 우려 때문에 Patch로 설정
	@PatchMapping(path="/{id}")
	public ApiResponse<Integer> updateBoard(
			@PathVariable("id") @Valid Long id
		   ,@RequestBody @Valid BoardParam boardPrams){
		return ApiResponse.ok(boardService.update(id, boardPrams));
	}
	
	@DeleteMapping(path="/{id}")
	public ApiResponse<String> deleteBoard(
			@PathVariable("id") @Valid Long id){
		boardService.delete(id);
		return ApiResponse.ok("SUCCESS");
	}
}
