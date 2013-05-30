package com.bekkestad.examples.tutorial;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

@Entity
@NamedQueries({
	@NamedQuery(
			name=UserEntity.FIND_ALL, 
			query="select u from UserEntity u"),
	@NamedQuery(
			name=UserEntity.FIND_USER_BY_USERNAME, 
			query="select u from UserEntity u WHERE u.username = :username"),
			@NamedQuery(
					name=UserEntity.FIND_USER_BY_USERNAME_PASSWORD, 
					query="select u from UserEntity u WHERE u.username = :username and u.password = :password")
})
public class UserEntity {
	
	public static final String FIND_ALL = "findAll";
	public static final String FIND_USER_BY_USERNAME = "findUserByUsername";
	public static final String FIND_USER_BY_USERNAME_PASSWORD = "findUserByUsernamePassword";
	
	@Id
	@GeneratedValue
	private Long id;	
	private String username;	
	private String password;


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserEntity other = (UserEntity) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}


	public Long getId() {
		return id;
	}


	public String getPassword() {
		return password;
	}


	public String getUsername() {
		return username;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", username=" + username + "]";
	}
	
	@Transient
	public UserDAO asDAO(){
		UserDAO userDAO = new UserDAO();
		userDAO.setUserId(this.id);
		userDAO.setUsername(this.username);
		userDAO.setPassword(this.password);
		return userDAO;
	}
	
	public static UserEntity fromDAO(UserDAO userDAO){
		UserEntity userEntity = new UserEntity();
		userEntity.setId(userDAO.getUserId());
		userEntity.setUsername(userDAO.getUsername());
		userEntity.setPassword(userDAO.getPassword());
		
		return userEntity;
	}

}
