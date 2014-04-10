package com.josesa.websocket.chat;

import java.io.Serializable;

public class Message implements Serializable{

	private static final long serialVersionUID = -341658452058700240L;

	private String message;
	
	private String user;
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
}
