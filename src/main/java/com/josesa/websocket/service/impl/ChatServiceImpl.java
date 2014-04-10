package com.josesa.websocket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.josesa.websocket.chat.Message;
import com.josesa.websocket.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService{
	
	private final SimpMessagingTemplate template;
	
	private final MessageSendingOperations<String> messagingTemplate;

    @Autowired
    public ChatServiceImpl(SimpMessagingTemplate template, MessageSendingOperations<String> messagingTemplate) {
        this.template = template;
        this.messagingTemplate = messagingTemplate;
    }

	@Override
	public void send(Message msg) {
		messagingTemplate.convertAndSend("/topic/public", msg);
	}

}
