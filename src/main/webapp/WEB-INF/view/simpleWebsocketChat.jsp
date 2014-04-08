<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome to Simple Websocket Chat</title>
		<style type="text/css">
			#chatHistory{
				border:1px solid #ccc;
				border-radius: 5px 5px 5px 5px;
				height:100px;
				font-family: arial;
    			font-size: 0.8em;
    			padding: 10px;
    			overflow-y: auto;
			}
		</style>
		<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery.js"/>"></script>
		<script type="text/javascript">
			$(function() {
				var ws = new WebSocket("ws://"+window.location.host+"/spring-websocket-sample/simpleChatHandler");
				ws.onmessage = function (event) {
					console.log("got message "+event.data);
				  	$("#chatHistory").append('<div>'+event.data+'</div>');
					document.getElementById("chatHistory").scrollTop = document.getElementById("chatHistory").scrollHeight;
				  	//$("#chatHistory").animate({ scrollTop: $("#chatHistory div").last().scrollTop() }, 1000);
				};
				
				$("#sendChat").on("click", function(e) {
					sendText();
				});
				
				$("#chatText").on("keyup", function(e) {
					if(e.which==13){
						sendText();
					}
				});
				
				function sendText(){
					var text = $("[name=chatText]").val();
					if(text.length>0){
						console.log("Sending text "+text);	
						ws.send(text);
						$("[name=chatText]").val("");
						$("[name=chatText]").focus();
					}
				}
			});
		</script>
	</head> 
	<body>
		<h2>Simple WS C</h2>
		<div id="chatHistory">
		</div>
		<div><input id="chatText" name="chatText" type="text"/><input id="sendChat" type="button" value="Enviar"/></div>
	</body>
</html>
