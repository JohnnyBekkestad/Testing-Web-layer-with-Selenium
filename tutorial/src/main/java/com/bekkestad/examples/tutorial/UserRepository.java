package com.bekkestad.examples.tutorial;

import java.util.List;

import javax.ejb.Local;

@Local
public interface UserRepository {

	List<UserDAO> getAllUsers();
	UserDAO getUser(String username);	
	UserDAO getUser(String username, String password);
	Long addUser(UserDAO user);
	void updateUser(UserDAO user);
	void deleteUser(UserDAO user);
}
