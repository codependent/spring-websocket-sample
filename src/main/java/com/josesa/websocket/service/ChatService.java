package com.josesa.websocket.service;

import com.josesa.websocket.chat.Message;

public interface ChatService {

	void send(Message message);
	
}
