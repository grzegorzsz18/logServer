package com.semafors.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.semafors.exceptions.UserAlreadyLoggedException;
import com.semafors.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.semafors.dao.implementation.LogDAO;
import com.semafors.dao.implementation.UserDAO;
import com.semafors.entity.Log;
import com.semafors.entity.User;

@Service
public class LogService {
	@Autowired
	LogDAO logDAO;
	@Autowired
	UserDAO userDao;
	
	public static final String uploadingdir = System.getProperty("user.dir") + "/images/";

	public long addLog(Log log) {
		log.setLoginTime(System.currentTimeMillis());
		return logDAO.add(log);
	}

	public Log getByLogId(long logId) {
		return logDAO.getByLogId(logId);
	}

	public List<Log> getAll() {
		return logDAO.getAll();
	}

	public List<Log> getByUser(long userId) {
		return logDAO.getAllByUser(userId);
	}

	public Long getNumberOfActiveLogs() {
		return logDAO.getNumberOfActiveLogs();
	}

	public long login(String login, String password, MultipartFile uploadingFile) throws Exception {
		User user;
		Log log = new Log();
		log.setExpired(false);

		try {
			user = userDao.getByLogin(login);
		} catch (Exception e) {
			user = new User();
			log.setPositiveVerification(false);
		}
		if(user.getId()!= null && checkIsLogged(user)){
			throw new UserAlreadyLoggedException();
		}
		log.setPositiveVerification(true);
		log.setLoginTime(System.currentTimeMillis());
		if (log.isPositiveVerification() != false && user.getPassword().equals(password)) {
			log.setPositiveVerification(true);
			log.setUserId(user.getId());
		} else {
			log.setPositiveVerification(false);
		}
		long loginId = logDAO.add(log);

		File file = new File(uploadingdir + loginId + ".jpg");
		uploadingFile.transferTo(file);

		if(log.isPositiveVerification() == false) {
			throw new UserNotFoundException();
		}
		return loginId;
	}

	private boolean checkIsLogged(User user){
		return logDAO.getNotExpiredLogByUserId(user.getId()) > 0;
	}
}
