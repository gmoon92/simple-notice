package com.moong.notice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.moong.notice.domain.board.Board;
import com.moong.notice.domain.board.BoardType;
import com.moong.notice.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

/**
[Backend 사전과제]

### 과제
공지사항 웹 어플리케이션 구현

### 기능
* 사용자는 텍스트로 된 공지를 추가할 수 있다.
* 사용자는 공지를 수정/삭제할 수 있다.
* 사용자는 공지목록을 조회할 수 있다.
* 조회시 제목, 작성일, 작성자, 최종수정일, 내용이 조회 가능하다.
* 목록은 페이징 기능이 있다.

 * 단위/통합 테스트 케이스(선택)
 * 로그인 기능(선택)
 * REST API 작성(선택)
 * 제출시 프로젝트 빌드, 실행 방법 명시 (ex.README.md 파일에 설명을 기술)
 *  
### 과제 요구사항
 
 **/
@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing
public class NoticeApplication implements CommandLineRunner{

	private final BoardRepository boardRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(NoticeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Board> boards = new ArrayList<Board>();
		for(int i=0; i<30; i++) {
			boards.add(
					Board.builder()
						 .title("제목" + i)
						 .contents(new StringBuffer().append(i))
						 .type(BoardType.NOTICE)
						 .build()
					);
		}
		boardRepository.saveAll(boards);
	}
}