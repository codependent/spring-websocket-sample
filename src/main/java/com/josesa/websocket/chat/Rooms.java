package com.josesa.websocket.chat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class Rooms {
	
	public Map<String, Set<String>> roomMap = new HashMap<String, Set<String>>();
	
	public Rooms(){
		roomMap.put("public", new HashSet<String>());
	}

	public void enter(String roomName, String userName){
		roomMap.get(roomName).add(userName);
	}
}
