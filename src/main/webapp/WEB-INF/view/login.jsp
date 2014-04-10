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
		<form action="<c:url value="/j_security_check"/>" method="POST">
			<div>
				<input type="text" name="j_username"/>
			</div>
			<div>
				<input type="password" name="j_password"/>
			</div>
			<div>
				<input type="submit" name="entrar" value="Entrar"/>
			</div>
		</form>
	</body>
</html>
