package com.zzz.controller;


import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzz.util.HttpUtil;
@Controller
public class LoginController {
	@PostMapping("/login")
	@ResponseBody
	public String Login(Model model,HttpSession session,@RequestParam(defaultValue="") String account,@RequestParam(defaultValue="") String passwd){
		String sr=HttpUtil.sendPost("http://10.1.65.33:81/login", "account="+account+"&password="+passwd);
		return sr;
	}
}
