package com.semafors.service;

import java.util.List;

import com.semafors.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.semafors.dao.implementation.LogDAO;
import com.semafors.dao.implementation.UserDAO;
import com.semafors.entity.User;

@Service
public class UserService {

	@Autowired UserDAO userDao;
	@Autowired LogDAO logDao;
	
	public void addUser(User user) {
		userDao.add(user);
	}
	
	public User getById(long userId) {
		return userDao.getById(userId);
	}
	
	public List<User> getActiveUsers(){
		return userDao.getActiveUsers();
	}
	
	public void logout(String login) throws Exception{
		User user;
		try {
			user = userDao.getByLogin(login);
		}
		catch (Exception e){
			throw new UserNotFoundException();
		}
		logDao.logoutUser(user.getId(), System.currentTimeMillis());
	}
}
