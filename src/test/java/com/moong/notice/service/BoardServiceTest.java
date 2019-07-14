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
import com.moong.notice.domain.member.Member;
import com.moong.notice.domain.member.MemberRules;
import com.moong.notice.repository.BoardRepository;
import com.moong.notice.repository.MemberRepository;
import com.moong.notice.service.dto.BoardParam;
import com.moong.notice.service.dto.SearchParam;
import com.moong.notice.service.dto.SelectOptions;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardServiceTest {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	
	@Before
	public void setUp() {
		boardRepository.deleteAll();
		memberRepository.deleteAll();
	}
	
	@Test
	public void 게시판_페이징_조회() {
		List<Board> boards = new ArrayList<Board>();
		
		Member member = memberRepository.save(Member.builder()
										.rule(MemberRules.MEMBER)
										.uId("moong")
										.uPw("1234")
										.build());
			
		for(int i=0; i<10; i++) {
			boards.add(
					Board.builder()
						 .title("제목" + i)
						 .contents("감사합니다.")
						 .type(BoardType.NOTICE)
						 .build()
						 .setMember(member)
					);
		}
		boardRepository.saveAll(boards);
		
		SearchParam params = new SearchParam();
					params.setSta_ymd("2019-01-01");
					params.setEnd_ymd("2099-12-01");
					params.setKeyword("");
					params.setType(BoardType.NOTICE.getType());
					params.setOption_date(SelectOptions.CREATED_DATE.getOption());
					params.setOption_keyword(SelectOptions.TITLE.getOption());
		Page<Board> page = boardService.findAll(1, params);
		assertThat(page.getNumber(), is(0)); // 현제 페이지
		assertThat(page.getTotalPages(), is(2)); // 전체 페이지수
	}
	
	@Test
	public void 게시판_저장() {
		Member member = memberRepository.save(Member.builder()
										.rule(MemberRules.MEMBER)
										.uId("moong")
										.uPw("1234")
										.build());
		
		BoardParam saveBoard = BoardParam.builder()
										 .title("제목")
										 .type(BoardType.NOTICE)
										 .u_id("moong")
										 .member(member)
										 .build();
		
		assertThat(boardService.save(saveBoard).getTitle()).isEqualTo(saveBoard.getTitle());
	}
}
