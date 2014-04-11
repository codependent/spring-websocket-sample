package com.josesa.websocket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
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
		messagingTemplate.convertAndSend(msg.getDestination(), msg);
	}

	@Scheduled(fixedDelay=10000)
	public void sendAdvertisement() {
		Message msg = new Message();
		msg.setDestination("/topic/public");
		msg.setUser("Publicidad");
		msg.setMessage("Apúntate al gym");
		template.convertAndSend(msg.getDestination(), msg);
	}
	
}
