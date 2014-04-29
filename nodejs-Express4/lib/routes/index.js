var mysql = require("../model")

exports.login = function(req, res){
	console.log("---LOGIN--- nodeAuthToken: "+req.cookies.nodeAuthToken);
	var connection = mysql.getConnection();
	connection.connect();
	connection.query("SELECT * from authtoken where token = '" + req.cookies.nodeAuthToken+"'", 
		function(err, rows, fields) {
			if (err) throw err;
			if(rows !== undefined && rows.length==1){
				console.log("---LOGIN--- Token válido, se permite el acceso a "+rows[0].userName)
				req.session.user=rows[0].userName;
				res.send(200, 'logged');
			}else{
				res.send(401, 'unauthorized');
			}
		});
	connection.end();
};