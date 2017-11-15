package com.zzz.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;
import com.zzz.model.User;
import com.zzz.repository.UserRepository;
@Controller
public class VerifyController {
	@Autowired
	private UserRepository userRepository;
	@PostMapping("/verify")
	public String verify(Model model,HttpSession session ,@Valid User user,BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return "index";
		}
		String result=userRepository.auth(user.getAccount(),user.getPasswd());
		if(StringUtils.equals(result, "success")){
		List<User> list =userRepository.findbyid(user.getAccount());
		User user2=(User)list.get(0);
		session.setAttribute("path", user2.getPath());
		session.setAttribute("user", user2);
		session.setAttribute("name",user2.getName());
		session.setAttribute("user_id", user2.getUser_id());
		}
		else if(StringUtils.equals(result, "account_is_error")){
			model.addAttribute("p","帐号不存在!");		
			return "index";
		}
		else{
			model.addAttribute("p","帐号密码不匹配!");		
			return "index";
		}
		return "redirect:/task";
	}  
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	@PostMapping("/register")
	public String register1(Model model,@Valid User user,BindingResult bindingResult,@RequestParam("passwd1") String id){
		if(bindingResult.hasErrors()){
			System.out.print("asdasd");	
			return "register";
		}
		String result=userRepository.register(user.getAccount());
	    if(StringUtils.equals(result, "account_is_error")){
	    	user.setUser_id(UUID.randomUUID().toString());
	    	user.setPath("../images/index.png");
			userRepository.save(user);
			return "index";
		}
		else{
			model.addAttribute("p","帐号已经存在!");	
			return "register";
		}
	 
	}
}
