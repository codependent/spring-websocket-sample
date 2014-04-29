var constants = require("../util/constants.js");

exports.config = function(socketIO, server, sessionStore, cookieParser){
	
	var io = socketIO.listen(server);

	io.of('/topic/public').authorization(function (handshakeData, callback) {
		console.log("---SOCKET.IO AUTHORIZATION---");
		console.log("---SOCKET.IO AUTHORIZATION--- Handshake Cookie "+handshakeData.headers.cookie);
		console.log("---SOCKET.IO AUTHORIZATION--- Handshake Data Init ***********************");
		console.dir(handshakeData);
		console.log("---SOCKET.IO AUTHORIZATION--- Handshake Data End ***********************");
		
		// We use the Express cookieParser created before to parse the cookie
	    // Express cookieParser(req, res, next) is used initialy to parse data in "req.headers.cookie".
	    // Here our cookies are stored in "data.headers.cookie", so we just pass "data" to the first argument of function
	    cookieParser(handshakeData, {}, function(parseErr) {
	        if(parseErr) { return callback('Error parsing cookies.', false); }

	        // Get the SID cookie
	        var sidCookie = (handshakeData.secureCookies && handshakeData.secureCookies[constants.EXPRESS_SID_KEY]) ||
	                        (handshakeData.signedCookies && handshakeData.signedCookies[constants.EXPRESS_SID_KEY]) ||
	                        (handshakeData.cookies && handshakeData.cookies[constants.EXPRESS_SID_KEY]);
	        console.log("---SOCKET.IO AUTHORIZATION--- Unsigned SidCookie*"+sidCookie)
	        
	        sessionStore.load(sidCookie, function(err, session) {
	            // And last, we check if the used has a valid session and if he is logged in
	        	console.log("---SOCKET.IO AUTHORIZATION--- SessionStore[sidCookie]: " + session);
	            if (err || !session || session.user === undefined) {
	                callback('---SOCKET.IO AUTHORIZATION--- Not logged in', false);
	            } else {
	                // If you want, you can attach the session to the handshake data, so you can use it again later
	                // You can access it later with "socket.handshake.session"
	            	console.log("---SOCKET.IO AUTHORIZATION--- Logged user: "+session.user)
	                handshakeData.session = session;
	            	handshakeData.foo = 'baz';
	                callback(null, true);
	            }
	        });
	    });
	    
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
}