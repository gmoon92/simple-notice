package com.moong.notice.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.moong.notice.domain.board.BoardType;
import com.moong.notice.repository.BoardRepository;
import com.moong.notice.repository.MemberRepository;
import com.moong.notice.service.BoardService;
import com.moong.notice.service.dto.SelectOptions;


@RunWith(SpringRunner.class)
@WebMvcTest(value=BoardController.class)
public class BoardControllerTest {

	@Autowired 
	private MockMvc mockMvc;
	
	@MockBean private BoardService boardService;
	@MockBean private BoardRepository boardRepository;
	@MockBean private MemberRepository memberRepository;
	
	private String PREFIX_URL = "/api/board/"; 

	@Test
	public void 전체_게시판_조회() throws Exception{
		mockMvc.perform(get(PREFIX_URL+"/1"
								      +"?type="+BoardType.NOTICE.getType()
								      +"&option_date="+SelectOptions.CREATED_DATE.getOption()
								      +"&option_keyword="+SelectOptions.TITLE.getOption()
								      +"&sta_ymd=2019-01-01"
								      +"&end_ymd=2019-07-30"
								      ))
			   .andDo(print())
			   .andExpect(status().isOk())
			   ;
	}
}
