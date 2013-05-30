package com.bekkestad.examples.tutorial;

import javax.enterprise.inject.Model;

@Model
public class Credentials {

	private String username;
	private String password;
	
	public String getPassword() {
		return password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}
