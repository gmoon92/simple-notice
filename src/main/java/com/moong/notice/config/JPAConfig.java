package com.moong.notice.config;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.moong.notice.domain.board.Board;
import com.moong.notice.domain.board.BoardType;
import com.moong.notice.domain.member.Member;
import com.moong.notice.domain.member.MemberRules;
import com.moong.notice.repository.BoardRepository;
import com.moong.notice.repository.MemberRepository;

@EnableJpaAuditing
@Configuration
public class JPAConfig {

	@PersistenceContext
	private EntityManager em;

	@Bean
	public JPAQueryFactory jpaQueryFactory() { return new JPAQueryFactory(em); }

	@Bean
	public CommandLineRunner setUp(BoardRepository boardRepository
								,MemberRepository memberRepository){
		return (args) -> {
			Member user1 = memberRepository.save(Member.builder()
													   .uId("moong")
													   .uPw("1234")
													   .rule(MemberRules.ADMIN)
													   .name("문겸")
													   .build());
			Member user2 = memberRepository.save(Member.builder()
									       .uId("moong2")
									       .uPw("1234")
									       .rule(MemberRules.ADMIN)
									       .name("테스트")
									       .build());


			List<Board> boards = new ArrayList<Board>();

			for(int i=0; i<10; i++) {
				Board savedBoard = Board.builder()
										.title("제목"+i)
										.contents("내용")
										.type(BoardType.NOTICE)
										.build()
										.setMember( i%2==0 ? user1 : user2);
				boards.add(savedBoard);
			}
			boardRepository.saveAll(boards);
		};
	}
}
