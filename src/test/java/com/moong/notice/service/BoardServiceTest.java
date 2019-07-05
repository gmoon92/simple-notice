package com.moong.notice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import com.moong.notice.domain.board.Board;
import com.moong.notice.domain.board.BoardType;
import com.moong.notice.repository.BoardRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardServiceTest {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Before
	public void setUp() {
		boardRepository.deleteAll();
	}
	
	@Test
	public void 게시판_페이징_조회() {
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
		Page<Board> page = boardService.findAll(BoardType.NOTICE, 1, "제목");
		
		assertThat(page.getNumber(), is(1)); // 현제 페이지
		assertThat(page.getTotalPages(), is(3)); // 전체 페이지수
	}
	
	@Test
	public void 게시판_저장() {
		Board saveBoard = Board.builder()
							   .title("제목")
							   .type(BoardType.NOTICE)
							   .build();
		assertThat(boardService.save(saveBoard)).isEqualTo(saveBoard);
	}
	
}
