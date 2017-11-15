package com.zzz.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Task {
	@Id
	private String task_id;
	private String name;
	private String content;
	private Date date;
	@DateTimeFormat(style = "yyyy-MM-dd")
	private Date bydate;
	private Integer state;
	private Integer delay;
	@ManyToOne
	@JoinColumn(name="pro_id",unique=false)
	private Project project;
	@ManyToOne
	@JoinColumn(name="user_id",unique=false)
	private User user;
	@OneToMany(mappedBy="task")
	private Set<Comment> comment = new HashSet<Comment>();
	public String getTask_id() {
		return task_id;
	}
	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	public Set<Comment> getComment() {
		return comment;
	}
	public void setComment(Set<Comment> comment) {
		this.comment = comment;
	}
	public Integer getDelay() {
		return delay;
	}
	public void setDelay(Integer delay) {
		this.delay = delay;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getBydate() {
		return bydate;
	}
	public void setBydate(Date bydate) {
		this.bydate = bydate;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Set<Taskman> getTaskman() {
		return taskman;
	}
	public void setTaskman(Set<Taskman> taskman) {
		this.taskman = taskman;
	}

	@OneToMany(mappedBy="task")
	private Set<Taskman> taskman = new HashSet<Taskman>();


	
}
