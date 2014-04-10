package com.josesa.websocket.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.josesa.websocket.chat.Rooms;

@Controller
public class SimpleWebsocketChat {

	@Autowired
	private Rooms rooms;
	
	@RequestMapping("/login")
	public String simpleWebsocketChatLogin(String userName, String mode, HttpSession session, RedirectAttributes flash){
		session.setAttribute("userName", userName);
		flash.addAttribute("room","public");
		return "redirect:/"+mode;
	}
	
	@RequestMapping("/simpleWebsocketChat")
	public String simpleWebsocketChat(HttpSession session, String room){
		String userName = (String)session.getAttribute("userName");
		if(userName == null || userName.trim().equals("")){
			return "redirect:/index";
		}
		rooms.enter(room, userName);
		return "simpleWebsocketChat";
	}
	
	@RequestMapping("/simpleSockJSWebsocketChat")
	public String simpleSockJSWebsocketChat(HttpSession session, String room){
		String userName = (String)session.getAttribute("userName");
		if(userName == null || userName.trim().equals("")){
			return "redirect:/index";
		}
		rooms.enter(room, userName);
		return "simpleSockJSWebsocketChat";
	}
	
	@RequestMapping("/stompSockJSWebsocketChat")
	public String stompSockJSWebsocketChat(HttpSession session, String room){
		String userName = (String)session.getAttribute("userName");
		if(userName == null || userName.trim().equals("")){
			return "redirect:/index";
		}
		rooms.enter(room, userName);
		return "stompSockJSWebsocketChat";
	}
	
}
