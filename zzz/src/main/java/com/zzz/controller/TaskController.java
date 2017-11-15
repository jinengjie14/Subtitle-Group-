package com.zzz.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import com.zzz.model.User;
import com.zzz.repository.CommentRepository;
import com.zzz.repository.ProjectRepository;
import com.zzz.repository.TaskRepository;
import com.zzz.repository.TaskmanRepository;

@Controller
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private TaskmanRepository taskmanRepository;

	/**
	 * 任务页面的映射
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/task")
	public String task(Model model, HttpSession session) {
		if (session.getAttribute("name") == null) {// 判断是否登录
			return "index";
		}
		
		User user = (User) session.getAttribute("user");
		String user_id = user.getUser_id();
		String path = (String) session.getAttribute("path");
		String name = (String) session.getAttribute("name");
		List task=taskmanRepository.selecttask(user_id);
		List taskR=taskRepository.select(user);
		if(task.isEmpty()) {
			model.addAttribute("task1","还未参与任务.." );
		}else {
			model.addAttribute("task",task );
		}
		if(taskR.isEmpty()) {
			model.addAttribute("task2","请添加一个任务..");//查询当前用户参与的任务
		}else {
			model.addAttribute("pu",taskR);//查询当前用户参与的任务
		}
		model.addAttribute("name", name);
		model.addAttribute("path", path);
		
		
		return "task";
	}
	
	@GetMapping("/task_1")
	public String task_1(HttpSession session, Model model) {
		String id = (String) session.getAttribute("user_id");
		String path = (String) session.getAttribute("path");
		String name = (String) session.getAttribute("name");
		model.addAttribute("name", name);
		model.addAttribute("path", path);
		model.addAttribute("pro", projectRepository.select(id));
		return "task1";
	}

	@PostMapping("/task_save")
	public String Task_save(HttpSession session, @Valid Task task, @RequestParam("by_date") String bydate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> map = new HashMap<String, Object>();
		String pro_id = (String) session.getAttribute("proid");
		try {
			User user = (User) session.getAttribute("user");
			Date qwq = sdf.parse(bydate);
			String qwq1 = sdf.format(new Date());
			if (java.sql.Date.valueOf(qwq1).after(java.sql.Date.valueOf(bydate))) {
				
			} else {
				task.setTask_id(UUID.randomUUID().toString());
				task.setDate(new Date());
				task.setBydate(qwq);
				task.setUser(user);
				task.setState(0);
				task.setDelay(0);
				if (pro_id == null) {
					task.setProject(null);
				} else {
					List<Project> list = projectRepository.selectpro(pro_id);
					Project project = list.get(0);
					task.setProject(project);
					project.setMax(project.getMax() + 1);
					projectRepository.update(project);
				}
				taskRepository.save(task);
				
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return "redirect:/task";
	}

	@PostMapping("/task_update")
	@ResponseBody
	public Map<String, Object> Tuptade(@RequestParam("id") String task_id, @RequestParam("state") String newstate) {
		int state;
		Map<String, Object> map = new HashMap<String, Object>();
		if (task_id == null || newstate == null) {
			map.put("code", "400");

		} else {

			if (newstate.equals("true")) {
				state = 1;
				map.put("code", "200");
			} else {
				state = 0;
				map.put("code", "201");
			}
			List<Task> task1 = taskRepository.selectid(task_id);
			Task task = task1.get(0);
			task.setState(state);
			taskRepository.update(task);
			if (task.getProject() == null) {
			} else {
				Project project = task.getProject();
				if(state==1) {
				project.setComplete(project.getComplete() + 1);
				}else {
					project.setComplete(project.getComplete() - 1);
				}
				projectRepository.update(project);
			}
		}
		return map;
	}
	
}
