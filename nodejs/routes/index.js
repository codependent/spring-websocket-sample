exports.login = function(req, res){
	console.log("---LOGIN--- nodeAuthToken: "+req.cookies.nodeAuthToken);
	//TODO Acceso al usuario logado. A través del token recibido en esta petición se consulda en BBDD
	//se obtiene la info del usuario logado y se inserta en la sesión de express
	//sessionStore.user=req.body.token
	req.session.user=req.body.token;
	res.send(200, 'logged');
};