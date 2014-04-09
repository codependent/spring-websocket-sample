package com.josesa.websocket.web.websocket;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class SimpleChatHandler extends TextWebSocketHandler{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="simpleChatSessionMap")
	private Map<String, WebSocketSession> simpleChatSessionMap;
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		logger.info("received message {}",message.getPayload());
		
		if(!simpleChatSessionMap.containsKey(session.getId())){
			logger.info("adding new session {}",session.getId());
			simpleChatSessionMap.put(session.getId(), session);
		}
		String userName = (String)session.getAttributes().get("userName");
		TextMessage tm = new TextMessage((userName + ": "+message.getPayload()));

		for (Iterator<Entry<String, WebSocketSession>> iterator = simpleChatSessionMap.entrySet().iterator(); iterator.hasNext();) {
			WebSocketSession s = iterator.next().getValue();
			if(s.isOpen()){
				logger.info("sending message to WebSocketSession {}",s.getId());
				s.sendMessage(tm);
			}else{
				logger.info("removing closed session {}",s.getId());
				iterator.remove();
			}
		}
	}
}
