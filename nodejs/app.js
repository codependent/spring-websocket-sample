var express = require("express"),
app = express(),
server = require("http").createServer(app),
io = require("socket.io").listen(server);
app.use(express.static(__dirname + "/public"));

io.of('/topic/public').authorization(function (handshakeData, callback) {
	console.log("AUTHORIZATION!");
	console.dir(handshakeData);
	handshakeData.foo = 'baz';
	callback(null, true);
}).on('connection', function (socket) {
	console.log("CONNECTION!");
	console.dir(socket.handshake.foo);
	console.dir(socket.handshake.query.user);
	socket.on("chat", function(message) {
		console.log("CHAT!");
		console.log("sockets :" +io.sockets);
		console.log("received message " + message);
		socket.emit("processed chat", socket.handshake.query.user+": "+message);
		socket.broadcast.emit("processed chat", socket.handshake.query.user+": "+message);
	})

}).on('connect_failed', function (reason) {
    console.error('unable to connect to namespace', reason);
});

/*
io.sockets.on("connection", function(socket) {
	socket.on("/topic/public", function(message) {
		console.log("sockets :" +io.sockets);
		console.log("received message " + message);
		socket.emit("/topic/public", message);
	});
});*/

server.listen(3000);