var express = require("express");
var cookieParser = require('cookie-parser');
var bodyParser = require("body-parser");
var session = require('express-session');
var socketIO = require("socket.io");
var websocket = require("./lib/websocket");
var middleware = require("./lib/middleware");
var routes = require("./lib/routes");
var constants = require("./lib/util/constants.js");

var app = express();

var cookieParser = cookieParser(constants.COOKIE_SECRET);
//Create a new store in memory for the Express sessions
var sessionStore = new session.MemoryStore();

middleware.config(app, express, sessionStore, session, cookieParser, bodyParser);

app.post('/login', routes.login);

var server = require("http").createServer(app);
server.listen(3000);

websocket.config(socketIO, server, sessionStore, cookieParser);