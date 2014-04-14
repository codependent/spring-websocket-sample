<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome</title>
	</head> 
	<body>
		<c:url value="/home" var="messageUrl" />
		<form action="<c:url value="/enter"/>" method="POST">
			<div>
				<select name="mode">
					<option value="simpleWebsocketChat">Simple WS</option>
					<option value="simpleSockJSWebsocketChat">Simple SockJS WS</option>
					<option value="stompSockJSWebsocketChat">STOMP SockJS WS</option>
					<option value="nodeJsSocketIoChat">NodeJS socket.io WS</option>
				</select>
			</div>
			<div>
				<input type="submit" name="entrar" value="Entrar"/>
			</div>
		</form>
	</body>
</html>
