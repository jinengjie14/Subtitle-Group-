package com.zzz.controller;

import java.util.ArrayList;
import java.util.List;

import com.zzz.model.Project;

public class UserForm {
	private List<Project> project =new ArrayList<Project>();
	private String account;
	private String name;
	
	public List<Project> getProject() {
		return project;
	}
	public void setProject(List<Project> project) {
		this.project = project;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
