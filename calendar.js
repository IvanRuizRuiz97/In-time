var self;
function viewModel() {
	self = this;
	self.listaFichajesL = ko.observableArray([]);
	self.listaFichajesM = ko.observableArray([]);
	self.listaFichajesX = ko.observableArray([]);
	self.listaFichajesJ = ko.observableArray([]);
	self.listaFichajesV = ko.observableArray([]);
	self.listaFichajesS = ko.observableArray([]);
	self.listaFichajesD = ko.observableArray([]);

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
		self.fichajesUsuario = [];
			self.listaFichajesL([]);
			self.listaFichajesM([]);
			self.listaFichajesX([]);
			self.listaFichajesJ([]);
			self.listaFichajesV([]);
			self.listaFichajesS([]);
			self.listaFichajesD([]);
			self.fichajes = data.fichajes;
			for (let i = 0; i < self.fichajes.length; i++) {
				const fj = self.fichajes[i];
				const horaIn = fj.HoraI.split(':');
				const horaFi = fj.HoraF.split(':');
				let posTop = 0;
				let length = 0;
				const px = 50.18;
				const nmediaHora = 2;
				const mediaHora = 0.5;
				// Si los minutajes son distintos
				if (horaIn[1] !== horaFi[1]) {
					if (horaIn[1] < horaFi[1]) {
						length = (parseInt(horaFi[0], 10) - parseInt(horaIn[0], 10) + mediaHora) * nmediaHora * px;
					} else {
						length = (parseInt(horaFi[0], 10) - parseInt(horaIn[0], 10) - mediaHora) * nmediaHora * px;
					}
				} else {
					length = (parseInt(horaFi[0], 10) - parseInt(horaIn[0], 10)) * nmediaHora * px;
				}
				if ('30' === horaIn[1]) {
					posTop = (parseInt(horaIn[0], 10) + mediaHora) * nmediaHora * px;
				} else {
					posTop = (parseInt(horaIn[0], 10)) * nmediaHora * px;
				}
				aniadirReunion(posTop, length, fj, horaIn, horaFi);
			}
		
	};

	function aniadirReunion(posTop, length, reunion, horaIn, horaFi) {
		switch (reunion.dia) {
			case 'LUNES':
				if (self.listaFichajesL().some(r => r.name === reunion.name) === false) {
					self.listaFichajesL.push(new Fichaje(reunion.name, reunion.dia, horaIn[0], horaIn[1], horaFi[0], horaFi[1]));
				}
				estilizarLI(posTop, length, reunion);
				break;
			case 'MARTES':
				if (self.listaFichajesM().some(r => r.name === reunion.name) === false) {
					self.listaFichajesM.push(new Fichaje(reunion.name, reunion.dia, horaIn[0], horaIn[1], horaFi[0], horaFi[1]));
				}
				estilizarLI(posTop, length, reunion);
				break;
			case 'MIERCOLES':
				if (self.listaFichajesX().some(r => r.name === reunion.name) === false) {
					self.listaFichajesX.push(new Fichaje(reunion.name, reunion.dia, horaIn[0], horaIn[1], horaFi[0], horaFi[1]));
				}
				estilizarLI(posTop, length, reunion);
				break;
			case 'JUEVES':
				if (self.listaFichajesJ().some(r => r.name === reunion.name) === false) {
					self.listaFichajesJ.push(new Fichaje(reunion.name, reunion.dia, horaIn[0], horaIn[1], horaFi[0], horaFi[1]));
				}
				estilizarLI(posTop, length, reunion);
				break;
			case 'VIERNES':
				if (self.listaFichajesV().some(r => r.name === reunion.name) === false) {
					self.listaFichajesV.push(new Fichaje(reunion.name, reunion.dia, horaIn[0], horaIn[1], horaFi[0], horaFi[1]));
				}
				estilizarLI(posTop, length, reunion);
				break;
			case 'SABADO':
				if (self.listaFichajesS().some(r => r.name === reunion.name) === false) {
					self.listaFichajesS.push(new Fichaje(reunion.name, reunion.dia, horaIn[0], horaIn[1], horaFi[0], horaFi[1]));
				}
				estilizarLI(posTop, length, reunion);
				break;
			case 'DOMINGO':
				if (self.listaFichajesD().some(r => r.name === reunion.name) === false) {
					self.listaFichajesD.push(new Fichaje(reunion.name, reunion.dia, horaIn[0], horaIn[1], horaFi[0], horaFi[1]));
				}
				estilizarLI(posTop, length, reunion);
				break;
			default:
				break;
		}
	}

	function estilizarLI(posTop, length, fichaje) {

		const ulL = document.getElementById(fichaje.dia.toLowerCase());
		const itemsL = ulL.getElementsByTagName('li');
		for (let n = 0; n < itemsL.length; n++) {
			if (itemsL[n].innerText === fichaje.name) {
				itemsL[n].style.top = posTop.toString() + 'px';
				itemsL[n].style.height = length.toString() + 'px';
				if (fichaje.horaF === '0') {
					itemsL[n].style.background = '#3d5ce7c5';
				} else {
					itemsL[n].style.background = '#9eec8ac5';
				}
			}
		}
	}

	self.buscarPorSemana = function () {
		const info = {
			type: 'buscarPorSemana',
			nombre: sessionStorage.userName,
			semana: $('#selectSemana').val(),
			vista: "calendar"
		};
		self.sws.send(JSON.stringify(info));
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