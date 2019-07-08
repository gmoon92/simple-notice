package com.moong.notice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.moong.notice.domain.board.Board;
import com.moong.notice.domain.board.BoardType;
import com.moong.notice.domain.member.Member;
import com.moong.notice.domain.member.MemberRules;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MemberRepositoryTest {

	@Autowired private MemberRepository memberRepository;
	@Autowired private BoardRepository boardRepository;
	
	@Before
	public void setUp() {
		memberRepository.deleteAll();
		boardRepository.deleteAll();
	}
	
	@Test
	public void 회원_저장() {
		Member savedMember = Member.builder()
							  .rule(MemberRules.ADMIN)
							  .uId("moong")
							  .uPw("test")
							  .name("moongyeom")
							  .build();
		assertThat(memberRepository.save(savedMember)).isEqualTo(savedMember);
	}
	
	@Test
	public void 회원_게시판_저장() {
		Member savedMember = Member.builder()
								   .rule(MemberRules.ADMIN)
								   .uId("moong")
								   .uPw("test")
								   .name("moongyeom")
								   .build();
		//Member loginMember = memberRepository.save(savedMember);
		memberRepository.save(savedMember);
		Member loginMember = memberRepository.findByUId("moong");
		
		Board savedBoard = Board.builder()
								.type(BoardType.NOTICE)
								.title("제목")
								.contents(new StringBuffer().append("내용 테스트1"))
								.build();
		savedBoard.setMember(loginMember);
					// ^-- 회원 양방향 설정 
		
		assertThat(boardRepository.save(savedBoard).getMember())
					.isEqualToComparingFieldByField(loginMember);
	}
	
	@After
	public void logs() {
		memberRepository.findAll().forEach(System.err::println);
		boardRepository.findAll().forEach(System.err::println);
	}
}
