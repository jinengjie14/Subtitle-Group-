package com.zzz.controller;

import java.util.Date;

import com.zzz.model.User;

public class ProjectForm1 {
	private String pro_id;
	private String pro_name;
	private String content;
	private Date date;
	private Integer max;
	private Integer complete;
	private Integer finish;//該項目是否完成
	private double Completion;//項目的完成度
	private User user;
	private double yanqi;
	
	
	public double getYanqi() {
		return yanqi;
	}
	public void setYanqi(double yanqi) {
		this.yanqi = yanqi;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getPro_id() {
		return pro_id;
	}
	public void setPro_id(String pro_id) {
		this.pro_id = pro_id;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	public Integer getFinish() {
		return finish;
	}
	public void setFinish(Integer finish) {
		this.finish = finish;
	}	
	public double getCompletion() {
		return Completion;
	}
	public void setCompletion(double completion) {
		Completion = completion;
	}

	
}
