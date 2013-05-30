package com.bekkestad.examples.tutorial;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Singleton
@Lock(LockType.READ)
public class UserRepositoryImpl implements UserRepository {
	
	@PersistenceContext
	EntityManager em;

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<UserDAO> getAllUsers() {
		List<UserDAO> userDAOList = new ArrayList<UserDAO>();
		@SuppressWarnings("unchecked")
		List<UserEntity> users =  em.createNamedQuery(UserEntity.FIND_ALL).getResultList();
		for(UserEntity user : users){
			userDAOList.add(user.asDAO());
		}		
		return userDAOList;
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public UserDAO getUser(String username) throws NoResultException {
		UserEntity userEntity = null;
				
		try{
			userEntity = (UserEntity)em.createNamedQuery(UserEntity.FIND_USER_BY_USERNAME)
				.setParameter("userName", username).getSingleResult();
		}catch(NoResultException nre){
			throw new NoResultException("NO USER");
		}catch(Exception e){
			e.printStackTrace();
		}					
		return userEntity!=null?userEntity.asDAO():null;
	}
	
	@Override
//	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public UserDAO getUser(String username, String password) throws NoResultException {
		UserEntity userEntity = null;
				
		try{
			userEntity = (UserEntity)em.createNamedQuery(UserEntity.FIND_USER_BY_USERNAME_PASSWORD)
				.setParameter("username", username)
				.setParameter("password", password)
				.getSingleResult();
		}catch(NoResultException nre){
			throw new NoResultException("NO USER");
		}catch(Exception e){
			e.printStackTrace();
		}
					
		return userEntity!=null?userEntity.asDAO():null;
	}

	@Override
	@Lock(LockType.WRITE)
//	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Long addUser(UserDAO userDAO) {
		UserEntity userEntity = UserEntity.fromDAO(userDAO);
		
		try {
			em.persist(userEntity);
			em.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userEntity.getId();
		
	}

	@Override
	@Lock(LockType.WRITE)
//	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void updateUser(UserDAO userDAO) {		
		em.merge(UserEntity.fromDAO(userDAO));
		em.flush();
	}

	@Override
	@Lock(LockType.WRITE)
//	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public void deleteUser(UserDAO userDAO) {
		em.remove(UserEntity.fromDAO(userDAO));
		em.flush();		
	}

}
