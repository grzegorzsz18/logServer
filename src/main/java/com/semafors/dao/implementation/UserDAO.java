package com.semafors.dao.implementation;

import java.util.List;

import com.semafors.entity.User;

public interface UserDAO {
	void add(User user);
	User getByLogin(String login);
	User getById(long userId);
	List<User> getActiveUsers();
}
