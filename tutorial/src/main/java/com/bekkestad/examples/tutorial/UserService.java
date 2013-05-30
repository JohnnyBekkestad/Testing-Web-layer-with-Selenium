package com.bekkestad.examples.tutorial;

import java.util.List;

public interface UserService {

	void addUser(UserDAO user);
	
	void updateUser(UserDAO user);
	
	void removeUser(UserDAO user);
	
	UserDAO getUser(String username) throws Exception;
	
	UserDAO loginUser(String username, String password);
	
	List<UserDAO> getUsers();
}
