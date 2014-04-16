package com.josesa.websocket.service;

import com.josesa.websocket.chat.Message;

public interface ChatService {

	String generateNodeJsToken(String userName);
	void removeNodeJsToken(String userName);
	void send(Message message);
	void sendAdvertisement();
	
}
