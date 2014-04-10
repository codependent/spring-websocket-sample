package com.josesa.websocket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.josesa.websocket.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService{
	
	private SimpMessagingTemplate template;

    //@Autowired
    /*public ChatServiceImpl(SimpMessagingTemplate template) {
        this.template = template;
    }*/
}
