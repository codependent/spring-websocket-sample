package com.josesa.websocket.web.controller;

import java.security.Principal;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.josesa.websocket.service.ChatService;

@Controller
public class LogoutController {

	@Autowired
	private ChatService chatService;
	
	@RequestMapping("/logout")
	public String logout(Principal principal, HttpSession session, HttpServletResponse response){
		
		chatService.removeNodeJsToken(principal.getName());
		session.invalidate();
		
		Cookie cookie = new Cookie("nodeAuthToken", null);
		cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
		
		return "redirect:index";
	}
}
