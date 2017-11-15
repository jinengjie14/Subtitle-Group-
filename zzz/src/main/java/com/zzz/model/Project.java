package com.zzz.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Project {
	@Id
	private String pro_id;
	private String pro_name;
	private String content;
	private Date date;
	private Integer max;
	private Integer complete;
	private Integer finish;//該項目是否完成
	public Integer getFinish() {
		return finish;
	}
	public void setFinish(Integer finish) {
		this.finish = finish;
	}
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	public Integer getComplete() {
		return complete;
	}
	public void setComplete(Integer complete) {
		this.complete = complete;
	}
	@OneToMany(mappedBy="project")
	private Set<Task> task = new HashSet<Task>();
	@ManyToOne
	@JoinColumn(name="leader_id")
	private User user;
	@OneToMany(mappedBy="project")
	private Set<Projectman> projectman = new HashSet<Projectman>();

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getPro_id() {
		return pro_id;
	}
	public void setPro_id(String pro_id) {
		this.pro_id = pro_id;
	}
	public Set<Projectman> getProjectman() {
		return projectman;
	}
	public void setProjectman(Set<Projectman> projectman) {
		this.projectman = projectman;
	}
	public Set<Task> getTask() {
		return task;
	}
	public void setTask(Set<Task> task) {
		this.task = task;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
