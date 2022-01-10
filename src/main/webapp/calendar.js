var self;
function viewModel() {
	self = this;
	self.fichajesUsuario = ko.observableArray([]);
	self.fichajesAll = ko.observableArray([])
	self.isAdmin = ko.observable(sessionStorage.isAdmin)

	if ("localhost:8080" == window.location.host) {
		var url = 'ws://' + window.location.host + '/SIGETEquipo1';
	} else {
		var url = 'wss://' + window.location.host + '/SIGETEquipo1';
	}
	self.sws = new WebSocket(url);
	
	self.sws.onopen = function(event) {
		var msg = {
			type: "leerFichajeUsuario",
			nombre: sessionStorage.userName,
			
		};
		self.sws.send(JSON.stringify(msg));
		
	};

	self.sws.onmessage = function (event) {
		let data = event.data;
		data = JSON.parse(data);
		if(data.type == "all"){
			self.fichajes = data.fichajes;
			for (let i = 0; i < self.fichajes.length; i++) {
				const fj = self.fichajes[i];
				self.fichajesAll.push(fj);
				
				
			}

		}
		else if(data.type == "singular"){
			self.fichajes = data.fichajes;
			for (let i = 0; i < self.fichajes.length; i++) {
				const fj = self.fichajes[i];
				self.fichajesUsuario.push(fj);
				
				
			}
			if(self.isAdmin()){
				let msg = {
					type: "leer"
				};
				self.sws.send(JSON.stringify(msg));
			}

		}
		
		
			
			
			
	}
		
	
	

	
}

const vm = new viewModel();
ko.applyBindings(vm);