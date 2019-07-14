package com.moong.notice.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.moong.notice.domain.board.Board;
import com.moong.notice.domain.board.BoardType;
import com.moong.notice.domain.member.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor
public class BoardParam {
	
	@NotEmpty // 허용 [x] null, "" / 허용 " " 
	private String title;
	
	@NotEmpty // 허용 [x] null, "" / 허용 " "
	private String contents;
	
	@NotNull // 허용 [x] null  / 허용 "", " " 
	private BoardType type;
	
	@NotBlank // 허용 [x] null, "", " " 
	private String u_id;
	
	private Member member;
	
	@Builder
	public BoardParam(String title, String contents, BoardType type, String u_id, Member member) {
		this.title = title;
		this.contents = contents;
		this.type = type;
		this.u_id = u_id;
		this.member = member;
	}
	
	public Board toEntity(){
		Board board = Board.builder()
						   .title(title)
						   .type(type)
						   .contents(contents)
						   .build()
						   .setMember(member);
		return board;
	}
}
