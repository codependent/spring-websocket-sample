package com.josesa.websocket.web.websocket;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class SimpleChatHandler extends TextWebSocketHandler{
	
	@Resource(name="simpleChatSessionMap")
	private Map<String, WebSocketSession> simpleChatSessionMap;
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		if(!simpleChatSessionMap.containsKey(session.getId())){
			simpleChatSessionMap.put(session.getId(), session);
		}
		String userName = (String)session.getAttributes().get("userName");
		System.out.println("chat recibido "+message.getPayload());
		TextMessage tm = new TextMessage((userName + ": "+message.getPayload()));

		for (Iterator<Entry<String, WebSocketSession>> iterator = simpleChatSessionMap.entrySet().iterator(); iterator.hasNext();) {
			WebSocketSession s = iterator.next().getValue();
			if(s.isOpen()){
				s.sendMessage(tm);
			}else{
				iterator.remove();
			}
		}
	}
}
