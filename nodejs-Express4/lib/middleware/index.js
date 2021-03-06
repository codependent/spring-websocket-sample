var constants = require("../util/constants.js");

exports.config = function(app, express, sessionStore, session, cookieParser, bodyParser){

	app.use(express.static(__dirname + "/public"));
	app.use(cookieParser);
	app.use(bodyParser());
	app.use(session({ store: sessionStore, secret: constants.COOKIE_SECRET, key: constants.EXPRESS_SID_KEY}));

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
}