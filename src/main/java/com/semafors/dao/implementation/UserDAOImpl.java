package com.semafors.dao.implementation;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.semafors.entity.User;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO{

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public void add(User user) {
		entityManager.persist(user);	
	}

	@Override
	public User getById(long userId) {
		Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.id = :userId")
				.setParameter("userId",userId);
		return (User)query.getSingleResult();
	}

	@Override
	public List<User> getActiveUsers() {
		Query query = entityManager.createQuery("SELECT u FROM User u, Log l WHERE l.expired = false and l.userId = u.id");
		return (List<User>)query.getResultList();
	}

	@Override
	public User getByLogin(String login) throws NullPointerException{
		Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.login = :login")
				.setParameter("login", login);
		return (User)query.getSingleResult();
	}

}
