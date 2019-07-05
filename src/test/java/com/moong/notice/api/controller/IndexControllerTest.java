package com.moong.notice.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class IndexControllerTest {

	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new IndexController()).build();
	}
	
	@Test
	public void testIndexPath() throws Exception {
		this.mockMvc.perform(get("/")) // get / url 요청
					.andDo(print()) // 응답 내용 출력
					.andExpect(status().isOk()) // 응답 코드가 200 인지
					;
	}
}
