package com.zzz.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Taskman {

	@Id
	private String id;
	@ManyToOne
    @JoinColumn(name="user_id")
    private User user;
	@ManyToOne
    @JoinColumn(name="task_id")
    private Task task; 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	
}
