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
		<form action="<c:url value="/simpleWebsocketChat/login"/>" method="POST">
			<div>
				<input type="text" name="userName"/>
			</div>
			<div>
				<input type="submit" name="entrar" value="Entrar"/>
			</div>
		</form>
	</body>
</html>