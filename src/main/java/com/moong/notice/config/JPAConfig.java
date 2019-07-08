package com.moong.notice.config;

import java.util.ArrayList;
import java.util.List;

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

	@Bean
	public CommandLineRunner setUp(BoardRepository boardRepository
								,MemberRepository memberRepository){
		return (args) -> {
			Member loginMember = memberRepository.save(Member.builder()
															 .uId("moong")
															 .uPw("1234")
															 .rule(MemberRules.ADMIN)
															 .name("문겸")
															 .build());
			
			List<Board> boards = new ArrayList<Board>();
		
			for(int i=1; i<13; i++) {
				Board savedBoard = Board.builder()
										.title("제목"+i)
										.contents("내용")
										.type(BoardType.NOTICE)
										.build()
										.setMember(loginMember);
				boards.add(savedBoard);
			}
			boardRepository.saveAll(boards);
		};
	}
}
