package com.zzz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class IndexController {
	@GetMapping("/")
	public String index() {
		return "index";
	}
	@GetMapping("/task")
	public String task() {
		return "task";
	}	
	@GetMapping("/task_1")
	public String task_1() {
		return "task1";
	}	
	@GetMapping("/project")
	public String project() {
		return "project";
	}	
	@GetMapping("/project_details")
	public String project_details() {
		return "project_details";
	}	
}
