let cerrarSesion = function() {
	sessionStorage.removeItem("token");
	const info = {
		type: 'CerrarSesion',
		userName: sessionStorage.userName
	};

	const data = {
		data: JSON.stringify(info),
		url: 'cerrarSesion',
		type: 'post',
		contentType: 'application/json',
		success: function() {
			window.location.href = 'index.html';
		},
		error: function(response) {
			alert('NO SE PUDO CERRAR SESION');
		}
	};
	$.ajax(data);
};
let checkAccess = function() {
	const info = {
		type: 'CheckAccess',
		userName: sessionStorage.userName,
		token: sessionStorage.token,
		page: window.location.href
	};

	const data = {
		data: JSON.stringify(info),
		url: 'checkAccess',
		type: 'post',
		contentType: 'application/json',
		success: function() {
		},
		error: function(response) {
			sessionStorage.clear();
			window.location.href = 'index.html';
		}
	};
	$.ajax(data);
};
var self;
function ViewModel() {
	self = this;
	self.listaUsuarios = ko.observableArray([]);
	self.nombreUsuario = ko.observable('');

	if("localhost:8080"== window.location.host){
		var url = 'ws://' + window.location.host + '/SIGETEquipo1';
	}else{
		var url = 'wss://' + window.location.host + '/SIGETEquipo1';
	}
	self.sws = new WebSocket(url);

	self.sws.onopen = function(event) {

		var msg = {
			type: "leer",
			nombre: sessionStorage.userName,
			vista: "gestionUsuarios"
		};
		self.sws.send(JSON.stringify(msg));
	};

	self.sws.onmessage = function(event) {


		var data = event.data;
		data = JSON.parse(data);
		var users = data.usuarios;


		for (var i = 0; i < users.length; i++) {
			var usuario = users[i];
			if (self.listaUsuarios().some(u => u.name === usuario.name) === false) {
				self.listaUsuarios.push(new Usuario(usuario.name, usuario.email, usuario.password, usuario.rol));
			}
			if (sessionStorage.userName === usuario.name) {
				if (usuario.rol === "ASISTENTE") {
					document.getElementById('username').placeholder = sessionStorage.userName;
					document.getElementsByTagName('h1')[0].innerText = sessionStorage.userName;
				}
			}

		}


		for (var j = 0; j < users.length; j++) {
			var usuario = users[j];
			if (usuario.name === self.nombreUsuario()) {

				document.getElementById('username').placeholder = usuario.name;

			}
		}




		self.modificarUsuario = function() {


			if (document.getElementById("pwdn").value === document.getElementById("pwd2").value) {

				if (contrasenaValida($('#pwdn').val())) {
					let nombre;
					if (self.nombreUsuario() === '') {
						nombre = sessionStorage.userName;
					} else {
						nombre = self.nombreUsuario();
					}
					var p = {
						type: "modificar",
						nombre: nombre,
						pwd: document.getElementById("pwdn").value,
						email: document.getElementById("email").value
					};
					document.getElementById("pwda").style.backgroundColor = "green";
					document.getElementById("pwdn").style.backgroundColor = "green";
					document.getElementById("pwd2").style.backgroundColor = "green";
					document.getElementById("email").style.backgroundColor = "green";
					self.modificacionCorrecta();
					self.sws.send(JSON.stringify(p));
				} else {
					document.getElementById("pwdn").style.backgroundColor = "red";

				}
			} else {

				document.getElementById("pwd2").style.backgroundColor = "red";
				document.getElementById("pwdn").style.backgroundColor = "red";
			}




		};
		self.modificacionCorrecta = function() {

			// When site loaded, load the Popupbox First
			loadPopupBox();

			$('#container').click(function() {
				unloadPopupBox();
			});

			function unloadPopupBox() {    // TO Unload the Popupbox
				$('#popup_box').fadeOut("slow");
				$("#container").css({ // this is just for style        
					"opacity": "1"
				});
				window.location.href = window.location.href;
			}

			function loadPopupBox() {    // To Load the Popupbox

				var counter = 5;
				var id;
				$('#popup_box').fadeIn("slow");
				$("#container").css({ // this is just for style
					"opacity": "0.3"
				});

				id = setInterval(function() {
					counter--;
					if (counter < 0) {
						clearInterval(id);

						unloadPopupBox();
					} else {
						$("#countDown").text("it closed  after " + counter.toString() + " seconds.");
					}
				}, 500);

			}
		};

		function contrasenaValida(pwd) {

			if (pwd.length > 4 && tiene_numeros(pwd) && tiene_minuscula_y_mayuscula(pwd)) {
				document.getElementById("pwdn").style.backgroundColor = "green";
				return true;
			} else {
				document.getElementById("pwdn").style.backgroundColor = "red";
				return false;
			}


		}

		function tiene_numeros(texto) {
			let numeros = "0123456789";
			for (i = 0; i < texto.length; i++) {
				if (numeros.indexOf(texto.charAt(i), 0) !== -1) {
					return true;
				}
			}
			return false;
		}

		function tiene_minuscula_y_mayuscula(pwd) {
			let m = false;
			let M = false;
			for (i = 0; i < pwd.length; i++) {
				if (!esNumero(pwd.charAt(i)) && pwd.charAt(i) === pwd.charAt(i).toUpperCase()) {
					M = true;
				}
				if (!esNumero(pwd.charAt(i)) && pwd.charAt(i) === pwd.charAt(i).toLowerCase()) {
					m = true;
				}
			}


			return M && m;

		}

		function esNumero(digito) {
			let numeros = "0123456789";
			if (numeros.indexOf(digito, 0) !== -1) {
				return true;
			}
			return false;


		}





	};

	/*
	 * self.eliminarUsuario = function() {
	 * 
	 * 
	 * const info = { type: 'eliminar', nombre: $('#actividad').val(), usuario:
	 * document.getElementById("eliminar").options[document.getElementById("eliminar").selectedIndex].text,
	 * success : function() { sessionStorage.userName = $('#username').val();
	 * alert('Se ha eliminado correctamente'); }, error : function(response) {
	 * 
	 * alert('No se ha eliminado'); } }; self.sws.send(JSON.stringify(info)); };
	 */
	class Usuario {
		constructor(name, email, password, rol) {
			this.name = name;
			this.email = email;
			this.password = password;
			this.rol = rol;
		}
		eliminarUsuario() {
			var p = {
				type: "eliminar",
				nombre: this.name
			};
			self.sws.send(JSON.stringify(p));
			window.location.href = window.location.href;
		}
		ascender = function() {
			var p = {
				type: "ascender",
				nombre: this.name

			};
			self.sws.send(JSON.stringify(p));
			window.location.href = window.location.href;

		};

		infoUsuarios() {
			var p = {
				type: "infoUsuarios",
				nombre: this.name
			};
			self.nombreUsuario(this.name);

			self.sws.send(JSON.stringify(p));

		}



	}

}
var vm = new ViewModel();
ko.applyBindings(vm);
