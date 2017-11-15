package com.zzz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

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
import com.zzz.model.Taskman;
import com.zzz.model.User;
import com.zzz.repository.ProjectRepository;
import com.zzz.repository.ProjectmanRepository;
import com.zzz.repository.TaskRepository;
import com.zzz.repository.UserRepository;

@Controller
public class ProjectDetailsController {
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private ProjectmanRepository projectmanRepository;
	@Autowired
	private UserRepository userRepository;
	@GetMapping("/project_details")
	public String project_details(Model model,@RequestParam("pro_id") String proid,HttpSession session) {
		List<Project> list=projectRepository.selectpro(proid);
		Project project = list.get(0);
		List list1=taskRepository.selecttask(proid);
		ProjectForm pform = new ProjectForm();
		BeanUtils.copyProperties(list,pform);
		double a=project.getComplete();
		double a1=project.getMax();
		if(a1==0) {
			pform.setCompletion(0);
		}else {	double a2=(double)(a/a1);
		pform.setCompletion(a2);}
		String path = (String) session.getAttribute("path");
		String name = (String) session.getAttribute("name");
		model.addAttribute("name", name);
		model.addAttribute("path", path);
		model.addAttribute("task",list1);
		model.addAttribute("pro",pform);
		return "project_details";
	}

@PostMapping("/proname_update")
@ResponseBody
public Map<String, Object> nameupdate(@RequestParam("id") String id,@RequestParam("name") String name) {
	Map<String, Object> map = new HashMap<String, Object>();
	projectRepository.namupdate(id, name);
	map.put("code", "200");
	return map;
}
@PostMapping("/procontent_update")
@ResponseBody
public Map<String, Object> contentupdate(@RequestParam("id") String id,@RequestParam("content") String content) {
	Map<String, Object> map = new HashMap<String, Object>();
	if(id==null || content==null) {
		map.put("code", "400");
	}
	else {
		map.put("code", "200");
		projectRepository.contentupdate(id, content);;
	}
	return map;
}
	@GetMapping("/project_details1")
public String project_details1(HttpSession session,Model model){
	String pro_id = (String) session.getAttribute("proid");
	List<Project> list = projectRepository.selectpro(pro_id);
	//Task task = list3.get(0);
	//session.setAttribute("task", task);
	List man=projectmanRepository.select(pro_id);
	//List list = commentRepository.taskdetails(task_id);
	List pu=taskRepository.selectProjecttask(pro_id);
	List<User> user = userRepository.user1();
	//List list2 = taskmanRepository.select(task_id);
	String path = (String) session.getAttribute("path");
	String name = (String) session.getAttribute("name");
	model.addAttribute("name", name);
	model.addAttribute("path", path);
	model.addAttribute("man", man);
	model.addAttribute("user", user);
	model.addAttribute("pu", pu);
	model.addAttribute("details",list);
	return "project_details1";
}
/**
 * 1.验证该用户是否可以编辑项目
 * @param id
 * @param session
 * @return
 */
@PostMapping("/pro_edit")
@ResponseBody
public Map<String, Object> pro_edit(@RequestParam("id") String id,@RequestParam("proid") String proid,HttpSession session) {
	Map<String, Object> map = new HashMap<String, Object>();
	String user_id = (String) session.getAttribute("user_id");
  List<Project> list = projectRepository.selectpro(proid);
  Project project = list.get(0);
  session.setAttribute("project", project);
	session.setAttribute("proid", proid);

	if(id.equals(user_id)) {
		map.put("code", "200");
	}
	else {
		map.put("code", "400");
	}
	return map;
}
@PostMapping("/proman_save")
@ResponseBody
public Map<String, Object> taskman(@RequestParam("id") String id, HttpSession session) {
	Map<String, Object> map = new HashMap<String, Object>();
	List<User> list = userRepository.select(id);
	User user = list.get(0);
	Project project = (Project) session.getAttribute("project");
	List<Projectman> list1 = projectmanRepository.selectman(user,project);
	if(project.getUser().getUser_id().equals(id) || list1.size()>0) {
		map.put("code", "400");
	}else {
		Projectman man = new Projectman();
	man.setId(UUID.randomUUID().toString());
	man.setProject(project);
	man.setUser(user);
	projectmanRepository.save(man);
	map.put("code", "200");
	}
	return map;
}
}
