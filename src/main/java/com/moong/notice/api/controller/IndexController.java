package com.moong.notice.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@GetMapping(value={"", "/index", "/home"})
	public String index() {
		return "index";
	}
}
