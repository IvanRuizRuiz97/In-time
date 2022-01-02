if("localhost:8080"== window.location.host){
	var url = 'ws://' + window.location.host + '/SIGETEquipo1';
}else{
	var url = 'wss://' + window.location.host + '/SIGETEquipo1';
}
var sws = new WebSocket(url);

sws.onopen = function(event) {
	var msg = {
		type: "ready"
	};
	sws.send(JSON.stringify(msg));
}
sws.onmessage = function(event) {
	var data = event.data;
	data = JSON.parse(data);
	console.log(data);
	sessionStorage.token = data.token;
	sessionStorage.isAdmin = data.isAdmin;
	window.location.href = "usuario.html";
}

let login = function() {
	const info = {
		type: 'Login',
		userName: $('#username').val(),
		pwd: $('#password').val()
	};
	sessionStorage.userName = $('#username').val();
	const data = {
		data: JSON.stringify(info),
		url: 'login',
		type: 'post',
		contentType: 'application/json',
		success: function(response) {
			sessionStorage.userName = $('#username').val();
			console.log(response);
			window.location.href = "usuario.html";
		},
		error: function(response) {
			document.getElementById("username").style.backgroundColor = "red";
			document.getElementById("password").style.backgroundColor = "red";


		}
	};
	$.ajax(data);
};