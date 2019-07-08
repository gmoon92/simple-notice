package com.moong.notice.api.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moong.notice.api.response.ApiResponse;
import com.moong.notice.aspect.BoardValidation;
import com.moong.notice.domain.board.BoardType;
import com.moong.notice.domain.member.MemberRules;
import com.moong.notice.service.BoardService;
import com.moong.notice.service.dto.BoardParam;

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
	public ApiResponse<?> getBoards(
			 @PathVariable("page") Integer page
			,@RequestParam(value="type", required=true) Integer type
			,@RequestParam(value="keyword", required=false, defaultValue="") String keyword
			)
	{
		return ApiResponse.ok(boardService.findAll(BoardType.of(type), page-1, keyword));
	}
	
	@BoardValidation
	@PostMapping(path="/")
	public ApiResponse<?> createBoard(@RequestBody @Valid BoardParam board ){
		return ApiResponse.ok(boardService.save(board));
	}
	
	@BoardValidation
	//@PutMapping(path="/{id}") // <- 전체 리소스 바뀔 우려 때문에 Patch로 설정
	@PatchMapping(path="/{id}")
	public ApiResponse<?> updateBoard(
			@PathVariable("id") @Valid Long id
		   ,@RequestBody @Valid BoardParam boardPrams){
		return ApiResponse.ok(boardService.update(id, boardPrams));
	}
	
	@BoardValidation(value=MemberRules.ADMIN)
	@DeleteMapping(path="/{id}")
	public ResponseEntity<String> deleteBoard(
			@PathVariable("id") @Valid Long id){
		boardService.delete(id);
		return ResponseEntity.ok("SUCCESS");
	}
	
}
