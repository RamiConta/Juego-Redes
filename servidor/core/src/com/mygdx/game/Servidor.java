package com.mygdx.game;

public class Servidor {
	
		private HiloServidor hs;
		
		public Servidor(PantallaJuego game) {
			hs = new HiloServidor(game);
			hs.start();

			 UtilesRed.hs = hs;
		}
		
		public void cerrarHilo() {
			hs.fin();
	}
}

