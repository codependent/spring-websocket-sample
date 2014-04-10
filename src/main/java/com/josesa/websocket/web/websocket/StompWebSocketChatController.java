package com.josesa.websocket.web.websocket;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.josesa.websocket.chat.Message;
import com.josesa.websocket.service.ChatService;

@Controller
public class StompWebSocketChatController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ChatService chatService;
	
	@MessageMapping("/chat")
	public void chat(Message message/*, Principal principal*/) {
		logger.info("received msg {}",message.toString());
		chatService.send(message);
	}	
	
	@SubscribeMapping("/topic/public")
	public void susc1(Principal principal) throws Exception {
		logger.info("aki1");
	}
	
	@SubscribeMapping("/public")
	public void susc2(Principal principal) throws Exception {
		logger.info("aki2");
	}
}
