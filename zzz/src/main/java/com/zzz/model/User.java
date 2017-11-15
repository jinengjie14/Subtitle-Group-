package com.zzz.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User {
	@Id
	private String user_id;
	@NotNull
	@Size(min=2,max=20)
	private String account;
	@NotNull
	@Size(min=2,max=18)
	private String passwd;
	private String name;
	private String path;
	@OneToMany(mappedBy="user")
	private Set<Task> task = new HashSet<Task>();
	
	@OneToMany(mappedBy="user")
	private Set<Comment> comment = new HashSet<Comment>();
	
	@OneToMany(mappedBy="user")
	private Set<Project> project = new HashSet<Project>();
	
	@OneToMany(mappedBy="user")
	private Set<Projectman> projectman = new HashSet<Projectman>();
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public Set<Task> getTask() {
		return task;
	}
	public void setTask(Set<Task> task) {
		this.task = task;
	}
	public Set<Project> getProject() {
		return project;
	}
	public void setProject(Set<Project> project) {
		this.project = project;
	}
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Projectman> getProjectman() {
		return projectman;
	}

	public void setProjectman(Set<Projectman> projectman) {
		this.projectman = projectman;
	}
	public Set<Comment> getComment() {
		return comment;
	}
	public void setComment(Set<Comment> comment) {
		this.comment = comment;
	}
	public Set<Taskman> getTaskman() {
		return taskman;
	}
	public void setTaskman(Set<Taskman> taskman) {
		this.taskman = taskman;
	}
	@OneToMany(mappedBy="user")
	private Set<Taskman> taskman = new HashSet<Taskman>();

}
