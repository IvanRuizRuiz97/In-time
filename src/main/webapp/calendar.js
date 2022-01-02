var self;
function viewModel() {
	self = this;
	self.fichajesUsuario = ko.observableArray([]);

	if ("localhost:8080" == window.location.host) {
		var url = 'ws://' + window.location.host + '/SIGETEquipo1';
	} else {
		var url = 'wss://' + window.location.host + '/SIGETEquipo1';
	}
	self.sws = new WebSocket(url);
	
	self.sws.onopen = function(event) {
		var msg = {
			type: "leer",
			nombre: sessionStorage.userName,
			
		};
		self.sws.send(JSON.stringify(msg));
	};

	self.sws.onmessage = function (event) {
		let data = event.data;
		data = JSON.parse(data);
		
			
			self.fichajes = data.fichajes;
			for (let i = 0; i < self.fichajes.length; i++) {
				const fj = self.fichajes[i];
				self.fichajesUsuario.push(fj);
				
				
			}
			
	}
		
	
	

	class Fichaje {
		constructor(name, dia, horaI, minutosI, horaF, minutosF) {
			this.name = name;
			this.dia = dia;
			this.horaI = horaI;
			this.minutosI = minutosI;
			this.horaF = horaF;
			this.minutosF = minutosF;
			
		}
	}
}

const vm = new viewModel();
ko.applyBindings(vm);