package com.semafors.dao.implementation;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.semafors.entity.Log;

public interface LogDAO {
	public long add(Log log);
	public Log getByLogId(long logId);
	public List<Log> getAll();
	public List<Log> getAllByUser(long userId);
	public long getNumberOfActiveLogs();
	public void logoutUser(long userId, long time);
}
