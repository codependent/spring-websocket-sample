var express = require("express");
var app = express();
var server = require("http").createServer(app);

var EXPRESS_SID_KEY = 'express.sid';
var COOKIE_SECRET = 'keyboard cat';
var cookieParser = express.cookieParser(COOKIE_SECRET);

//Create a new store in memory for the Express sessions
var sessionStore = new express.session.MemoryStore();

app.use(function(req, res, next) {
	var origin = req.get("Origin")
	console.log("---CORS MIDDLEWARE--- Origin: " + origin);
    res.header('Access-Control-Allow-Origin', origin);
    res.header('Access-Control-Allow-Credentials', "true");
    res.header('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE,OPTIONS');
    res.header('Access-Control-Allow-Headers', 'Cookie, Content-Type, Authorization, Content-Length, X-Requested-With');
    res.header('Access-Control-Expose-Headers', 'Set-Cookie, X-Powered-By');
    
    // intercept OPTIONS method
    if ('OPTIONS' == req.method) {
      res.send(200);
    }else{
      next();
    }
});
app.use(express.static(__dirname + "/public"));
app.use(express.cookieParser());
app.use(express.json());       // to support JSON-encoded bodies
app.use(express.urlencoded()); // to support URL-encoded bodies
app.use(express.session({ store: sessionStore, secret: COOKIE_SECRET, key: EXPRESS_SID_KEY}));

app.post('/login', function(req, res){
	console.log("---LOGIN---")
	console.log("---LOGIN--- nodeAuthToken: "+req.cookies.nodeAuthToken);
	//TODO Acceso al usuario logado. A través del token recibido en esta petición se consulda en BBDD
	//se obtiene la info del usuario logado y se inserta en la sesión de express
	//sessionStore.user=req.body.token
	req.session.user=req.body.token;
	res.send(200, 'logged');
});

var io = require("socket.io").listen(server);

io.of('/topic/public').authorization(function (handshakeData, callback) {
	console.log("---SOCKET.IO AUTHORIZATION---");
	console.log("---SOCKET.IO AUTHORIZATION--- Handshake Cookie "+handshakeData.headers.cookie);
	console.log("---SOCKET.IO AUTHORIZATION--- Handshake Data ***********************");
	console.dir(handshakeData);
	console.log("---SOCKET.IO AUTHORIZATION--- Handshake Data ***********************");
	
	// We use the Express cookieParser created before to parse the cookie
    // Express cookieParser(req, res, next) is used initialy to parse data in "req.headers.cookie".
    // Here our cookies are stored in "data.headers.cookie", so we just pass "data" to the first argument of function
    cookieParser(handshakeData, {}, function(parseErr) {
        if(parseErr) { return callback('Error parsing cookies.', false); }

        // Get the SID cookie
        var sidCookie = (handshakeData.secureCookies && handshakeData.secureCookies[EXPRESS_SID_KEY]) ||
                        (handshakeData.signedCookies && handshakeData.signedCookies[EXPRESS_SID_KEY]) ||
                        (handshakeData.cookies && handshakeData.cookies[EXPRESS_SID_KEY]);
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

server.listen(3000);