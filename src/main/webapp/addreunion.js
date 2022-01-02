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
	self.usuariosSeleccionados = ko.observableArray([]);
	if("localhost:8080"== window.location.host){
		var url = 'ws://' + window.location.host + '/SIGETEquipo1';
	}else{
		var url = 'wss://' + window.location.host + '/SIGETEquipo1';
	}
	self.sws = new WebSocket(url);

	self.sws.onmessage = function(event) {
		var data = event.data;
		data = JSON.parse(data);
		self.listaUsuarios.removeAll();
		var users = data;
		for (var i = 0; i < users.length; i++) {
			var usuario = users[i];
			if (self.listaUsuarios().some(u => u.name === usuario.name) === false) {
				self.listaUsuarios.push(new Usuario(usuario.name, usuario.email, usuario.password, usuario.rol));
			}
		}
	};


	self.check = function() {
		var dateInicio = $('#horaInicio').val().split(":");
		var dateFinal = $('#horaFinal').val().split(":");

		if ((dateInicio[1] === "30" || dateInicio[1] === "00") && (dateFinal[1] === "30" || dateFinal[1] === "00")) {
			document.getElementById("horaInicio").style.background = "white";
			document.getElementById("horaFinal").style.background = "white";
			const info = {
				type: 'check',
				nombre: $('#actividad').val(),
				dia: document.getElementById("dia").options[document.getElementById("dia").selectedIndex].text,
				horaInicio: dateInicio[0],
				horaFinal: dateFinal[0],
				minutoInicio: dateInicio[1],
				minutoFinal: dateFinal[1],
				semana: $('#semana').val()
			};

			self.sws.send(JSON.stringify(info));
		} else {
			document.getElementById("horaInicio").style.background = "red";
			document.getElementById("horaFinal").style.background = "red";

		}


	};
	self.convocarCorrectamente = function() {

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
			window.location.href = "usuario.html";

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

	self.addReunion = function() {
		for (var i = 0; i < self.listaUsuarios().length; i++) {
			if (document.getElementsByClassName("form-check-input")[i].checked === true) {
				self.usuariosSeleccionados.push(document.getElementsByClassName("form-check-label")[i].innerHTML);
			}
		}
		var dateInicio = $('#horaInicio').val().split(":");
		var dateFinal = $('#horaFinal').val().split(":");

		const info = {
			type: 'convocarReunion',
			nombre: $('#actividad').val(),
			dia: document.getElementById("dia").options[document.getElementById("dia").selectedIndex].text,
			horaInicio: dateInicio[0],
			horaFinal: dateFinal[0],
			minutoInicio: dateInicio[1],
			minutoFinal: dateFinal[1],
			usuarios: self.usuariosSeleccionados(),
			semana: $('#semana').val()

		};
		self.convocarCorrectamente();
		self.sws.send(JSON.stringify(info));
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
