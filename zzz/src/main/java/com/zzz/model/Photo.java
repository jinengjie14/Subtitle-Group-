package com.zzz.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Photo {
	@Id
	private String id;
	private String name;
	private String path;
	private String time;
	private String share;
	private String title;
	private String Size;
	public String getSize() {
		return Size;
	}
	public void setSize(String size) {
		Size = size;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@ManyToOne
	@JoinColumn(name = "userid")
	private User user;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getShare() {
		return share;
	}
	public void setShare(String share) {
		this.share = share;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Photo(String id, String name, String path, String time, String share, User user,String Size) {
		super();
		this.id = id;
		this.name = name;
		this.path = path;
		this.time = time;
		this.share = share;
		this.user = user;
		this.Size=Size;
	}
	public Photo() {
		super();
	}
	

}
