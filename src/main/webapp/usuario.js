let entrada = function() {
	const info = {
		type: 'entrada',
		userName: sessionStorage.userName,
		token: sessionStorage.token,
		page: window.location.href
	};

	const data = {
		data: JSON.stringify(info),
		url: 'entrada',
		type: 'post',
		contentType: 'application/json',
		success: function() {
			alert("Entrada registrada")
		},
		error: function(response) {
			sessionStorage.clear();
			window.location.href = 'index.html';
		}
	};
	$.ajax(data);
};

let salida = function() {
	const info = {
		type: 'salida',
		userName: sessionStorage.userName,
		token: sessionStorage.token,
		page: window.location.href
	};

	const data = {
		data: JSON.stringify(info),
		url: 'salida',
		type: 'post',
		contentType: 'application/json',
		success: function() {
			alert("Salida Registrada")
		},
		error: function(response) {
			sessionStorage.clear();
			window.location.href = 'index.html';
		}
	};
	$.ajax(data);
};
