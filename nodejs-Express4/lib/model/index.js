var mysql = require('mysql');

exports.getConnection=function(){
	var connection =mysql.createConnection({
		  host : 'localhost',
		  port : "3306",
		  user : 'root',
		  database : "ws",
		  password : 'XXXXXX'
		}
	);
	return connection;
}