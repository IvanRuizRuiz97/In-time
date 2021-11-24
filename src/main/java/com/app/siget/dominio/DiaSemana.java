package com.app.siget.dominio;

public enum DiaSemana {
	LUNES {
		
		@Override
		public String toString() {
			return "LUNES";
		}

		@Override
		public int getValue() {
			return 0;
		}
	},
	MARTES {
		@Override
		public String toString() {
			return "MARTES";
		}

		@Override
		public int getValue() {
			return 1;
		}
	},
	MIERCOLES {
		@Override
		public String toString() {
			return "MIERCOLES";
		}

		@Override
		public int getValue() {
			return 2;
		}
	},
	JUEVES {
		@Override
		public String toString() {
			return "JUEVES";
		}

		@Override
		public int getValue() {
			return 3;
		}
	},
	VIERNES {
		@Override
		public String toString() {
			return "VIERNES";
		}

		@Override
		public int getValue() {
			return 4;
		}
	},
	SABADO {
		@Override
		public String toString() {
			return "SABADO";
		}

		@Override
		public int getValue() {
			return 5;
		}
	},
	DOMINGO {
		@Override
		public String toString() {
			return "DOMINGO";
		}

		@Override
		public int getValue() {
			return 6;
		}
	};

	public abstract int getValue();

}