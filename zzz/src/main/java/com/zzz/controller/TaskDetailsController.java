package com.zzz.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zzz.model.Comment;
import com.zzz.model.Project;
import com.zzz.model.Task;
import com.zzz.model.Taskman;
import com.zzz.model.User;
import com.zzz.repository.CommentRepository;
import com.zzz.repository.ProjectRepository;
import com.zzz.repository.TaskRepository;
import com.zzz.repository.TaskmanRepository;
import com.zzz.repository.UserRepository;

@Controller
public class TaskDetailsController {
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private TaskmanRepository taskmanRepository;
    @GetMapping("/task_details")
    public String task_details(Model model,HttpSession session) {
    	String task_id = (String) session.getAttribute("task_id");
		List<Task> list3 = taskRepository.selectid(task_id);
		Task task = list3.get(0);
		session.setAttribute("task", task);
    	List list = commentRepository.taskdetails(task_id);
    	List<User> user = userRepository.user1();
    	List list2 = taskmanRepository.select(task_id);
    	if(task.getProject()!=null){
    		model.addAttribute("pro_name", task.getProject().getPro_name());
    		model.addAttribute("pro_id", task.getProject().getPro_id());
    	}
    	else{
    		model.addAttribute("pro_name", "未分配项目");
    		model.addAttribute("pro_id", "1");
    	}
    	String path = (String) session.getAttribute("path");
		String name = (String) session.getAttribute("name");
		model.addAttribute("name", name);
		model.addAttribute("path", path);
    	model.addAttribute("man", list2);
    	model.addAttribute("user", user);
    	model.addAttribute("comment", list);
    	model.addAttribute("details", task);
    	return "task_details";
    }
	@PostMapping("/task_details")
	@ResponseBody
	public Map<String, Object> details(@RequestParam("id") String task_id, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		session.setAttribute("task_id", task_id);
		map.put("code", "200");
		return map;
	}
	@PostMapping("/comment_save")
	@ResponseBody
	public Map<String, Object> comment_save(@RequestParam("content") String content, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		Task task = (Task) session.getAttribute("task");
		User user = (User) session.getAttribute("user");
		Comment comment = new Comment();
		comment.setId(UUID.randomUUID().toString());
		comment.setComment_content(content);
		comment.setUser(user);
		comment.setDate(new Date());
		comment.setTask(task);
		commentRepository.save(comment); 
		map.put("code", "200");
		return map;
	}
	@PostMapping("/taskname_update")
	@ResponseBody
	public Map<String, Object> nameupdate(@RequestParam("id") String id,@RequestParam("name") String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		taskmanRepository.namupdate(id, name);
		map.put("code", "200");
		return map;
	}
	@PostMapping("/taskcontent_update")
	@ResponseBody
	public Map<String, Object> contentupdate(@RequestParam("id") String id,@RequestParam("content") String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(id==null || content==null) {
			map.put("code", "400");
		}
		else {
			map.put("code", "200");
		taskmanRepository.contentupdate(id, content);;
		}
		return map;
	}
	@PostMapping("/taskdate_update")
	@ResponseBody
	public Map<String, Object> dateupdate(@RequestParam("id") String id,@RequestParam("ndate") String ndate) {
		Map<String, Object> map = new HashMap<String, Object>();
				map.put("code", "200");
				taskmanRepository.dateupdate(id, ndate);
		return map;
	}
	@PostMapping("/taskman_save")
	@ResponseBody
	public Map<String, Object> taskman(@RequestParam("id") String id, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<User> list = userRepository.select(id);
		User user = list.get(0);
		Task task = (Task) session.getAttribute("task");
		List<Taskman> list1 = taskmanRepository.selectman(user,task);
		if(task.getUser().getUser_id().equals(id) || list1.size()>0) {
			map.put("code", "400");
		}else {
		Taskman man = new Taskman();
		man.setId(UUID.randomUUID().toString());
		man.setTask(task);
		man.setUser(user);
		taskmanRepository.save(man);
		map.put("code", "200");
		}
		return map;
	}
	@PostMapping("pro_select")
	@ResponseBody
	public Map<String, Object> proselect(HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		String user_id = (String) session.getAttribute("user_id");
		List list = projectRepository.select(user_id);
		map.put("pro", list);
		map.put("code", "200");
		return map;
	}
	@PostMapping("pro_update")
	@ResponseBody
	public Map<String, Object> proupdate(@RequestParam("proid") String proid,HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		String id = (String) session.getAttribute("task_id");
		Task task = (Task) session.getAttribute("task");
		List<Project> pro = projectRepository.selectpro(proid);
		Project project = pro.get(0);
		if(task.getProject()==null) {
			project.setMax(project.getMax()+1);
			projectRepository.update(project);
			taskmanRepository.proupdate(id, project);
		}
		if(task.getProject().getPro_id().equals(proid)) {
			map.put("code", "404");
		}else {
			List<Project> pro1 = projectRepository.selectpro(task.getProject().getPro_id());
			Project project1 = pro1.get(0);
			project1.setMax(project1.getMax()-1);
			project.setMax(project.getMax()+1);
			projectRepository.update(project1);
			projectRepository.update(project);
		}
		

		return map;
	}
	
}
