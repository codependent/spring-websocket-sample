package com.josesa.websocket.web.controller;

import java.math.BigInteger;
import java.security.Principal;
import java.security.SecureRandom;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.josesa.websocket.chat.Rooms;
import com.josesa.websocket.service.ChatService;

@Controller
public class WebSocketChatController {

	@Autowired
	private Rooms rooms;
	
	@Autowired
	private ChatService chatService;
	
	@RequestMapping("/enter")
	public String simpleWebsocketChatLogin(Principal principal, String mode, HttpSession session, RedirectAttributes flash){
		session.setAttribute("userName", principal.getName());
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
	
	@RequestMapping("/nodeJsSocketIoChat")
	public String nodeJsSocketIoChat(Principal principal, HttpServletRequest request, HttpServletResponse response, String room){
		if(principal == null){
			return "redirect:/index";
		}
		if(!hasNodeAuthCookie(request)){
			String token = chatService.generateNodeJsToken(principal.getName());
			response.addCookie(generateNodeAuthCookie(token));
		}
		rooms.enter(room, principal.getName());
		return "nodeJsSocketIoChat";
	}
	
	private boolean hasNodeAuthCookie(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		boolean hasCookie = false;
		for (int i = 0; i < cookies.length && !hasCookie; i++) {
			Cookie cookie = cookies[i];
			if(cookie.getName().equals("nodeAuthToken")){
				hasCookie=true;
			}
		}
		return hasCookie;
	}
	
	private Cookie generateNodeAuthCookie(String token){
		Cookie c = new Cookie("nodeAuthToken", token);
		c.setSecure(false);
		c.setMaxAge(-1);
		c.setPath("/");
		return c;
	}
}
