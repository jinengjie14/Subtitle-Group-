package com.zzz.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzz.model.Project;
import com.zzz.model.Projectman;
import com.zzz.model.Task;
import com.zzz.model.User;
import com.zzz.repository.ProjectRepository;
import com.zzz.repository.ProjectmanRepository;
import com.zzz.repository.TaskRepository;
import com.zzz.repository.UserRepository;

@Controller
public class ProjectController {
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private ProjectmanRepository projectmanRepository;
	@Autowired
	private UserRepository userRepository;
	/**
	 * 1.項目网页的映射
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/project")
	public String project(HttpSession session,Model model) {
		String userid = (String) session.getAttribute("user_id");
		List<Project> list=projectRepository.select(userid);//查询到有该用户的项目
		List<ProjectForm> projectForm=new ArrayList<ProjectForm>();
		for(Project pro:list) {
			ProjectForm pform = new ProjectForm();
			BeanUtils.copyProperties(pro,pform);
			double a=pro.getComplete();
			double a1=pro.getMax();
			if(a1==0) {
				pform.setCompletion(0);
			}else {	double a2=(double)(a/a1);
			pform.setCompletion(a2);}
			projectForm.add(pform);
		}
		User user = (User)session.getAttribute("user");
		List<Project> list1 =projectRepository.selectuser(user);//查询是否有项目的负责人是该用户，查询出来
		List<ProjectForm1> projectForm1=new ArrayList<ProjectForm1>();
		for(Project pro:list1) {
			ProjectForm1 pform = new ProjectForm1();
			BeanUtils.copyProperties(pro,pform);
			double a=pro.getComplete();
			double a1=pro.getMax();
			if(a1==0) {
				pform.setCompletion(0);
			}else {	double a2=(double)(a/a1);
			pform.setCompletion(a2);}
			projectForm1.add(pform);
		}
		String path = (String) session.getAttribute("path");
		String name = (String) session.getAttribute("name");
		model.addAttribute("name", name);
		model.addAttribute("path", path);
		if(list.isEmpty()) {
			model.addAttribute("pro1","还未参与项目.." );
		}else {
			model.addAttribute("pu", projectForm);	//根据当前登录的用户id
		}
		if(list1.isEmpty()) {
			model.addAttribute("pro2","请添加一个项目..");
		}else {
			model.addAttribute("user", projectForm1);//根据leader_id
		}
		
	
		
		return "project";
	}

	
	/**
	 * 1.删除该项目以及与此项目相关的一切数据
	 * @param id
	 * @param session
	 * @param proid
	 * @return
	 */
	@PostMapping("/pro_delete")
	@ResponseBody
	public Map<String, Object> pro_delete(@RequestParam("id") String id,HttpSession session,@RequestParam("proid") String proid) {
		String user_id = (String) session.getAttribute("user_id");
	    List<Project> list = projectRepository.selectpro(proid);//根据项目id查询
		Project project = list.get(0);
		List<Task> tlist = taskRepository.prodelete(project);//根据项目id查询出要删除的任务
		List<Projectman> plist = projectmanRepository.mandelete(project);//根据项目id查询出要删除的项目参与人
		for(Task t1:tlist) {//遍历要删除的任务
			taskRepository.delete(t1);
		}
		for(Projectman p1:plist) {//遍历要删除的任务参与人
			projectmanRepository.delete(p1);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(id.equals(user_id)) {//如果当前登录的用户是该项目的负责人则可以删除
			projectRepository.delete(project);
			map.put("code", "200");
		}
		else {//否则提示没有权限
			map.put("code", "400");
		}
		return map;
	}


	/**
	 * 项目创建页面的映射
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/project1")
	public String project1(HttpSession session,Model model) {
		String path = (String) session.getAttribute("path");
		String name = (String) session.getAttribute("name");
		model.addAttribute("name", name);
		model.addAttribute("path", path);
		return "project1";
	}
	/**
	 * 保存数据到数据库
	 * @param session
	 * @param project
	 * @return
	 */
	@PostMapping("/pro_save")
	public String pro_save(HttpSession session,@Valid Project project){

		   User user = (User) session.getAttribute("user");
		   project.setPro_id(UUID.randomUUID().toString());
		   project.setDate(new Date());
		   project.setUser(user);
		   project.setMax(0);
		   project.setComplete(0);
		   project.setFinish(0);
		   projectRepository.save(project);
		return "redirect:/project";
	}
	
}
