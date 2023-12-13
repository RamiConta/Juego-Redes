package com.mygdx.game;

import java.net.InetAddress;

public class JugadorRed {

	private Chico jugador;
	public InetAddress ip;
	public int puerto;
	
	
	public JugadorRed(Chico jugador, InetAddress ip, int puerto) {
		this.jugador = jugador;
		this.ip = ip;
		this.puerto = puerto;
	}
	
	public Chico getEntidadJugador() {
		return jugador;
	}
}