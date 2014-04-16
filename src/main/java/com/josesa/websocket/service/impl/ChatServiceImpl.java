package com.josesa.websocket.service.impl;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.servlet.http.Cookie;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.josesa.websocket.chat.Message;
import com.josesa.websocket.dao.AuthTokenDAO;
import com.josesa.websocket.entity.AuthToken;
import com.josesa.websocket.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService{
	
	private final SimpMessagingTemplate template;
	private final MessageSendingOperations<String> messagingTemplate;
	private final AuthTokenDAO authTokenDAO;
	
	private SecureRandom random = new SecureRandom();
	
    @Autowired
    public ChatServiceImpl(SimpMessagingTemplate template, 
    					   MessageSendingOperations<String> messagingTemplate,
    					   AuthTokenDAO authTokenDAO) {
        this.template = template;
        this.messagingTemplate = messagingTemplate;
        this.authTokenDAO = authTokenDAO;
    }

	@Override
	public void send(Message msg) {
		messagingTemplate.convertAndSend(msg.getDestination(), msg);
	}
	
	@Transactional
	@Override
	public String generateNodeJsToken(String userName) {
		AuthToken authToken = authTokenDAO.findByUserName(userName);
		if(authToken!=null){
			authTokenDAO.delete(authToken);
		}
		String token = generateRandomToken();
		AuthToken a = new AuthToken();
		a.setToken(token);
		a.setUserName(userName);
		authTokenDAO.save(a);
		return token;
	}

	@Scheduled(fixedDelay=10000)
	public void sendAdvertisement() {
		Message msg = new Message();
		msg.setDestination("/topic/public");
		msg.setUser("Publicidad");
		msg.setMessage("Apúntate al gym");
		template.convertAndSend(msg.getDestination(), msg);
	}

	private String generateRandomToken(){
		String randomToken = new BigInteger(130, random).toString(32);
		return randomToken;
	}
	
}
