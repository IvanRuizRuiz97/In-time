$('#displayNone').click(function(e) {
	$('#hide-me').css('visibility', 'visible');

	if ($('#hide-me').is(":visible")) {
		$('#hide-me').css('display', 'none');
	} else {
		$('#hide-me').css('display', 'block');
	}
});

$('#visibilityHidden').click(function(e) {
	$('#hide-me').css('display', 'block');

	if ($('#hide-me').css('visibility') != 'hidden') {
		$('#hide-me').css('visibility', 'hidden');
	} else {
		$('#hide-me').css('visibility', 'visible');
	}
});

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
	self.nombreUsuario = ko.observable("");
	self.usuariosSeleccionados = ko.observableArray([]);
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
			vista: "addactividad"
		};
		self.sws.send(JSON.stringify(msg));
	};

	self.sws.onmessage = function(event) {
		document.getElementsByTagName('h1')[0].innerText = sessionStorage.userName;
		var data = event.data;
		data = JSON.parse(data);
		var users = data.usuarios;
		for (var i = 0; i < users.length; i++) {
			var usuario = users[i];
			if (self.listaUsuarios().some(u => u.name === usuario.name) === false) {
				self.listaUsuarios.push(new Usuario(usuario.name, usuario.email, usuario.password, usuario.rol));
			}
		}
	};

	self.aniadirActividad = function() {
		self.usuariosSeleccionados.push($('#select').val());
		var dateInicio = $('#horaInicio').val().split(":");
		var dateFinal = $('#horaFinal').val().split(":");

		if ((dateInicio[1] === "30" || dateInicio[1] === "00") && (dateFinal[1] === "30" || dateFinal[1] === "00")) {
			document.getElementById("horaInicio").style.background = "white";
			document.getElementById("horaFinal").style.background = "white";
			const info = {
				type: 'insertar',
				nombre: $('#nombreActividad').val(),
				dia: document.getElementById("dia").options[document.getElementById("dia").selectedIndex].text,
				horaInicio: dateInicio[0],
				horaFinal: dateFinal[0],
				minutoInicio: dateInicio[1],
				minutoFinal: dateFinal[1],
				usuarios: document.getElementById("select").options[document.getElementById("select").selectedIndex].text,
				semana: $('#semana').val()
			};
			console.log(info);
			self.actividadCreada();
			self.sws.send(JSON.stringify(info));

		} else {
			document.getElementById("horaInicio").style.background = "red";
			document.getElementById("horaFinal").style.background = "red";
		}
	};
	self.actividadCreada = function() {

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

	class Usuario {
		constructor(name, email, password, rol) {
			this.name = name;
			this.email = email;
			this.password = password;
			this.rol = rol;
		}
	}
}
var vm = new ViewModel();
ko.applyBindings(vm);
