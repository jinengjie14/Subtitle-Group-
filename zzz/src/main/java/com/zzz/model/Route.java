package com.zzz.model;

public class Route {
	private String name;
	private String time;
	private String Size;
	private String path;
	private String Fullname;
	private String route;
	
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public Route(String name, String time, String size, String path, String fullname, String route) {
		super();
		this.name = name;
		this.time = time;
		Size = size;
		this.path = path;
		Fullname = fullname;
		this.route = route;
	}
	public String getFullname() {
		return Fullname;
	}
	public void setFullname(String fullname) {
		Fullname = fullname;
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
	public String getSize() {
		return Size;
	}
	public void setSize(String size) {
		Size = size;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Route() {
		super();
	}
	
}
