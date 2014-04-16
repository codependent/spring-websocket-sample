var express = require("express");
var socketIO = require("socket.io");
var websocket = require("./websocket");
var middleware = require("./middleware");
var routes = require("./routes");
var constants = require("./util/constants.js");
var app = express();
var server = require("http").createServer(app);

var cookieParser = express.cookieParser(constants.COOKIE_SECRET);
//Create a new store in memory for the Express sessions
var sessionStore = new express.session.MemoryStore();

middleware.config(app,express,sessionStore);

app.post('/login', routes.login);

server.listen(3000);

websocket.config(socketIO,server,sessionStore,cookieParser);