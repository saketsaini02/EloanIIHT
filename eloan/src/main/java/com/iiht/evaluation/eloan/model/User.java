package com.iiht.evaluation.eloan.model;

public class User {
	
	private String username;
	private String password;
	private String id;
	private String role;
	public User() {
		
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public User(String username, String password, String id, String role) {
		super();
		this.username = username;
		this.password = password;
		this.id = id;
		this.role = role;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + "]";
	}
	

}
