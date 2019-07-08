package com.moong.notice.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moong.notice.domain.member.Member;
import com.moong.notice.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {
	private final MemberRepository memberRepository;
	
	@RequestMapping(value= {"/", "/home", "index"})
	public String home(HttpServletRequest request) {
		Member loginUser = memberRepository.findByUId("moong");
		HttpSession session = request.getSession(true);
		session.setAttribute("loginUser", loginUser);
		return "/notice";
	}

}
