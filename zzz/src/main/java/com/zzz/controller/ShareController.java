package com.zzz.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzz.model.Photo;
import com.zzz.repository.PhotoRepository;

@Controller
public class ShareController {
	@Autowired
	private PhotoRepository photoRepository;
	@GetMapping("/share")//查看分享
	public String input(Model model,HttpServletRequest request) {
		List<Photo> list=photoRepository.share("1");//取出数据库中确定分享的数据
		List<Photo> us = new ArrayList<Photo>();
		if(null != list && list.size()>0)//判断是否有值
		{
		for(int i=0;i<list.size();i++){
			Photo user2=(Photo)list.get(i);
			us.add(new Photo(user2.getId(),user2.getName(),user2.getPath(),user2.getTime(),user2.getShare(),user2.getUser(),user2.getSize()));//将数据写入数组
		}
		model.addAttribute("pu", us);//提交到网页
		}
		else{
			model.addAttribute("title", "目前没有人分享！");
		}
		return "drive2";
	}
	@PostMapping("/share1")//点击分享
	@ResponseBody
	public Map<String, Object> share1(String Fullname,Model model,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(Fullname!=null){
			photoRepository.update("1",Fullname);//修改数据库的值
			map.put("code", "200");
		}else{
			map.put("code", "400");
		}
		return map;
	}
}
