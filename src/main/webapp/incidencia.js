/**
 * 
 */
var self;
function viewModel() {
	self = this;
	self.incidenciasAll = ko.observableArray([]);
	self.rol = ko.observable("");
	self.rol(sessionStorage.isAdmin);
	
	

	if ("localhost:8080" == window.location.host) {
		var url = 'ws://' + window.location.host + '/SIGETEquipo1';
	} else {
		var url = 'wss://' + window.location.host + '/SIGETEquipo1';
	}
	self.sws = new WebSocket(url);
	
	self.sws.onopen = function(event) {
		var msg = {
			type: "leerIncidencias",
			nombre: sessionStorage.userName,
			
		};
		self.sws.send(JSON.stringify(msg));
	};

	self.sws.onmessage = function (event) {
		let data = event.data;
		data = JSON.parse(data);
		
			
			self.incidencias = data.incidencias;
			for (let i = 0; i < self.incidencias.length; i++) {
				const aux = self.incidencias[i];
				let inc = new Incidencia(aux.id,formatDate(aux.fecha),aux.tipo,aux.name,aux.body);
				self.incidenciasAll.push(inc);
			}
			
	}

	function formatDate(number){
		
		let cadena = "";
		let date = new Date(number);
		cadena += date.getDate() + "/";
		cadena += (date.getMonth()+1) + "/";
		cadena += date.getFullYear();
		return cadena;

	}

	
	

	
	
		class Incidencia{
			constructor(id,fecha,tipo,name,body) {
				this.id = id;
				this.fecha = fecha;
				this.tipo = tipo;
				this.name = name;
				this.body = body;
			}

		}
	
	
	
}

const vm = new viewModel();
ko.applyBindings(vm);


