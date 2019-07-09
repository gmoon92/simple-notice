package com.moong.notice.repository;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.criterion.CriteriaQuery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

import com.moong.notice.domain.board.Board;
import com.moong.notice.domain.board.BoardType;
import com.moong.notice.service.dto.SearchParam;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BoardRepositoryTest {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private EntityManager em;
	
	@Before
	public void setUp() {
		boardRepository.deleteAll();
	}
	
	@Test
	public void 게시판_페이징_조회() {
		for(int i=0; i<30; i++) {
			boardRepository.save(
						Board.builder()
							 .title("테스트"+i)
							 .type(BoardType.NOTICE)
							 .build()
						);
		}
		Pageable pageable = PageRequest.of(0 // 조회할 페이지 번호
										  ,3 // 페이지 수
										  );
		Page<Board> boards =  boardRepository.findByTypeAndTitleContains(BoardType.NOTICE,"테스트", pageable);
		assertThat(boards.getContent()).hasSize(3);
	}
	
	@Test
	public void 게시판_Create() {
		Board newBoard1 = Board.builder()
							   .title("공지사항 게시판입니다.")
							   .contents("감사합니다.")
							   .type(BoardType.NOTICE)
							   .build();
		
		boardRepository.save(newBoard1);
		
		Board newBoard2 = Board.builder()
							   .title("일반 게시판입니다.")
							   .contents("감사합니다.")
							   .type(BoardType.DEFAULT)
							   .build();

		boardRepository.save(newBoard2);
		
		assertThat(boardRepository.findAll())
				  .hasSize(2)
				  .contains(newBoard2);
	}
	
	@Test
	public void 게시판_Update() {
		Board savedBoard = boardRepository.save(Board.builder()
										  .type(BoardType.NOTICE)
										  .title("제목1")
										  .build());
				
		Integer isUpdate = boardRepository.updateBoard(savedBoard.getId(), Board.builder()
										  .type(BoardType.NOTICE)
										  .title("테스트")
										  .build());
		
		assertThat(1, is(isUpdate));
	}
	
	@Test
	public void 게시판_Read() {
		List<Board> savedBoards = asList(
							Board.builder()
								 .type(BoardType.NOTICE)
								 .title("제목1")
								 .build()
								 
						   ,Board.builder()
						   		 .type(BoardType.NOTICE)
						   		 .title("제목2")
						   		 .build()
				);
		
		List<Board> boards = boardRepository.saveAll(savedBoards);
		assertThat(boards).hasSize(2)
						   .containsExactly(savedBoards.get(0)
										   ,savedBoards.get(1))
						   .isEqualTo(savedBoards)
		;
	}
	
	@Test
	public void 게시판_Delete() {
		List<Board> savedBoards = asList(
							Board.builder()
								 .type(BoardType.NOTICE)
								 .title("제목1")
								 .build()
								 
						   ,Board.builder()
						   		 .type(BoardType.NOTICE)
						   		 .title("제목2")
						   		 .build()
				 );
		boardRepository.saveAll(savedBoards);
		boardRepository.deleteALLByIdQuery(boardRepository.findAllIdQuery());
		assertThat(boardRepository.count(), is(0L));
	}
	
	@After
	public void logs() {
		boardRepository.findAll()
		   			   .forEach(System.err::println);
	}
	
}