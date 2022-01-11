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
	self.listaReuniones = ko.observableArray([]);
	if("localhost:8080"== window.location.host){
		var url = 'ws://' + window.location.host + '/SIGETEquipo1';
	}else{
		var url = 'wss://' + window.location.host + '/SIGETEquipo1';
	}
	self.sws = new WebSocket(url);


	self.sws.onopen = function(event) {
		var msg = {
			type: "reunionesPendientes",
			nombre: sessionStorage.userName,

		};
		self.sws.send(JSON.stringify(msg));
	};
	
	self.sws.onmessage = function(event) {
		var data = event.data;
		data = JSON.parse(data);
		self.listaReuniones.removeAll();
		var reuniones = data;

		for (var i = 0; i < reuniones.length; i++) {
			var reunion = reuniones[i];
			const horaIn = reunion.HoraI.split(':');
			const horaFi = reunion.HoraF.split(':');
			if (self.listaReuniones().some(u => u.name === reunion.name) === false) {
				self.listaReuniones.push(new Reunion(reunion.id, reunion.name, reunion.dia, horaIn[0], horaIn[1], horaFi[0], horaFi[1]));
			}
		}
	};

	class Reunion {
		constructor(id, name, dia, horaI, minutosI, horaF, minutosF) {
			this.id = id;
			this.name = name;
			this.dia = dia;
			this.horaI = horaI;
			this.minutosI = minutosI;
			this.horaF = horaF;
			this.minutosF = minutosF;
		}

		rechazarReunion() {
			var p = {
				type: "rechazarReunion",
				nombre: sessionStorage.userName,
				id: this.id
			};
			self.sws.send(JSON.stringify(p));
			for (var i = 0; i < self.listaReuniones().length; i++) {
				if (self.listaReuniones()[i].nombre === this.nombre)
					self.listaReuniones.splice(i, 1);
			}
		}
		aceptarReunion() {
			var p = {
				type: "aceptarReunion",
				nombre: sessionStorage.userName,
				id: this.id
			};
			self.sws.send(JSON.stringify(p));


		}
	}


}
var vm = new ViewModel();
ko.applyBindings(vm);
