package com.semafors.dao.implementation;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.semafors.entity.Log;

@Repository
@Transactional
public class LogDAOImpl implements LogDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public long add(Log log) {
		entityManager.persist(log);
		Query query = entityManager.createQuery("SELECT l FROM Log l WHERE l.loginTime = :time", Log.class)
				.setParameter("time",log.getLoginTime());
		Log ret = (Log)query.getSingleResult();
		return ret.getId();
	}

	@Override
	public Log getByLogId(long logId) {
		Query query = entityManager.createQuery("SELECT l FROM Log l WHERE l.id = :logId", Log.class)
				.setParameter("logId", logId);
		return (Log)query.getSingleResult();
	}

	@Override
	public List<Log> getAll() {
		Query query = entityManager.createQuery("SELECT l FROM Log l", Log.class);
		return (List<Log>)query.getResultList();
	}

	@Override
	public List<Log> getAllByUser(long userId) {
		Query query = entityManager.createQuery("SELECT l FROM Log l WHERE l.userId = :userId", Log.class)
				.setParameter("userId", userId);
		return (List<Log>)query.getResultList();
	}

	@Override
	public long getNumberOfActiveLogs() {
		Query query = entityManager.createQuery("SELECT COUNT(l) FROM Log l WHERE l.expired = false", Long.class);
		return (Long)query.getSingleResult();
	}

	@Override
	public void logoutUser(long userId, long time) {
		Query query = entityManager.createQuery("Update Log l SET l.expired = true, l.logoutTime = :time WHERE l.userId = :userId")
				.setParameter("userId", userId)
				.setParameter("time", time);
		query.executeUpdate();
		
	}
	
}
