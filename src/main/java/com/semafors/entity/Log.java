package com.semafors.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "log")
public class Log implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	long userId;
	long loginTime;
	boolean positiveVerification;
	boolean expired;
	long logoutTime;
	
	public long getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(long time) {
		this.loginTime = time;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public boolean isPositiveVerification() {
		return positiveVerification;
	}
	public void setPositiveVerification(boolean positiveVerification) {
		this.positiveVerification = positiveVerification;
	}
	public boolean isExpired() {
		return expired;
	}
	public void setExpired(boolean expired) {
		this.expired = expired;
	}
	public long getLogoutTime() {
		return logoutTime;
	}
	public void setLogoutTime(long logoutTime) {
		this.logoutTime = logoutTime;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
}
