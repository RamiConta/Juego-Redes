package com.mygdx.game;
	import java.io.IOException;
	import java.net.DatagramPacket;
	import java.net.DatagramSocket;
	import java.net.InetAddress;
	import java.net.SocketException;
	
public class HiloServidor extends Thread {

		private DatagramSocket socket;
		private boolean fin = false;
		private JugadorRed[] jugadores;
		private int cantidadDeConexiones, maxConexiones = 2;
		private PantallaJuego game;
		public boolean empezo = false;

		
		public HiloServidor(PantallaJuego game) {
			jugadores = new JugadorRed[maxConexiones];
			this.game = game;
			
			try {
				socket = new DatagramSocket(36758);
				System.out.println("servidor iniciado");

			} catch (SocketException e) {
				fin = true;
				//e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
		    while (!fin) {
		        
		    	byte[] datos = new byte[1024];
		        DatagramPacket dp = new DatagramPacket(datos, datos.length);
		        System.out.println("Esperando mensaje...");
		       
		        try {
		            socket.receive(dp);
		            procesarMensaje(dp);
		        } catch (IOException e) {
		            //e.printStackTrace();
		        }
		    }
		    System.out.println("Hilo del servidor finalizado.");
		}

private void procesarMensaje(DatagramPacket dp) {
	String msg = new String(dp.getData()).trim();//trim() lo que hace es sacar los espacios
	String[] mensajeCompuesto = msg.split("#");
		
switch (mensajeCompuesto[0]) {

case "conectar":
	if(cantidadDeConexiones == 0) {//El servidor esta vacio
		jugadores[0] = new JugadorRed(game.getChico1(), dp.getAddress(), dp.getPort());//Guardar los datos del primer jugador
					game.getChico1().id = 0;
					enviarMensaje("conectado#" + 0, jugadores[0].ip, jugadores[0].puerto);//cuando el jugador se conecta le manda un mensaje al jugador con un id
					cantidadDeConexiones++;//aumenta la cantidad de conexiones
					
					System.out.println(cantidadDeConexiones + "Jugador conectado");
					
				}else if(cantidadDeConexiones == 1) {//Si ya hay un jugador en el servidor
					jugadores[1] = new JugadorRed(game.getChico2(), dp.getAddress(), dp.getPort());//crea al segundo
					game.getChico2().id = 1;
					enviarMensaje("conectado#"+1, jugadores[1].ip, jugadores[1].puerto);//le envia el "ok"
					cantidadDeConexiones++;
					
					System.out.println(cantidadDeConexiones + " Jugador conectado");
				}
				
				if(cantidadDeConexiones == maxConexiones) {//Si el numero de jugadores conectados es igual al maximo permitido, empieza el juego
					enviarMensaje("empezar");
					empezo = true;
				}
				
				break;
				
			case "Movimiento":
				if(empezo) {
				if(dp.getAddress().equals(jugadores[0].ip) && dp.getPort() == jugadores[0].puerto) { //si la ip-puerto del paquete que llega es igual a la ip-puerto del jugador 0, entonces 
																									//el que manda el mensaje es el jugador 0				
					jugadores[0].getEntidadJugador().movPersonaje(mensajeCompuesto[1]); //la instruccion de movimiento esta dividida en 2 partes "movimiento#izquierda"
																						//por lo que mensajeCompuesto agarra la segunda instruccion y lo mueve en la direccion correspondiente.				
				enviarMensaje("ActualizarPosicion#0#" + jugadores[0].getEntidadJugador().posicion.x + "#" + jugadores[0].getEntidadJugador().posicion.y);
				System.out.println("actualizando posicion jugador 0");
				}
				else if(dp.getAddress().equals(jugadores[1].ip) && dp.getPort() == jugadores[1].puerto) {
					jugadores[1].getEntidadJugador().movPersonaje(mensajeCompuesto[1]);
					
					enviarMensaje("ActualizarPosicion#1#" +jugadores[1].getEntidadJugador().posicion.x + "#" + jugadores[1].getEntidadJugador().posicion.y);
				System.out.println("actualizando posicion jugador 1");
				}
				
				enviarMensaje("asegurarseDePosicion#jugador0#"+jugadores[0].getEntidadJugador().posicion.x + "#"+jugadores[0].getEntidadJugador().posicion.y);
				enviarMensaje("asegurarseDePosicion#jugador1#"+jugadores[1].getEntidadJugador().posicion.x + "#"+jugadores[1].getEntidadJugador().posicion.y);
				}
				break;
				
//			case "golpea":
//					if(mensajeCompuesto[1].equals("0")) {
//						game.getChico1().golpear();
//					}
//				break;
				
			case "bala":
				if(empezo) {
					
				if(mensajeCompuesto[1].equals("0")) {
					game.getChico1().disparo=true;
				}else if(mensajeCompuesto[1].equals("1")) {
					game.getChico2().disparo=true;
				}
			}
				
				break;
			case "desconectar":
				
				if(dp.getAddress().equals(jugadores[0].ip) && dp.getPort() == jugadores[0].puerto) {
					enviarMensaje("jugadorDesconectado", jugadores[1].ip, jugadores[1].puerto);	
					
				}else if(dp.getAddress().equals(jugadores[1].ip) && dp.getPort() == jugadores[1].puerto) {
					enviarMensaje("jugadorDesconectado", jugadores[0].ip, jugadores[0].puerto);				
				}
				cantidadDeConexiones = 0;
				
				jugadores[0].getEntidadJugador().posicion.x = 0;
				jugadores[0].getEntidadJugador().posicion.y = 0;
				
				jugadores[1].getEntidadJugador().posicion.x = jugadores[1].getEntidadJugador().xInicial;
				jugadores[1].getEntidadJugador().posicion.y = jugadores[1].getEntidadJugador().yInicial;
				System.out.println("Jugador desconectado");
				break;
				
			case "rotacion":
				if(empezo) {					
				
					if(Integer.parseInt(mensajeCompuesto[1]) == 0) {//Se fija el id del cliente
					jugadores[0].getEntidadJugador().rotacion = Float.parseFloat(mensajeCompuesto[2]);
					enviarMensaje("actualizarRotacion#1#"+mensajeCompuesto[2], jugadores[1].ip, jugadores[1].puerto ); // le manda la rotacion en la que el otro jugador debe estar
				
					}else if(Integer.parseInt(mensajeCompuesto[1]) == 1) {//Se fija el id del cliente
					jugadores[1].getEntidadJugador().rotacion = Float.parseFloat(mensajeCompuesto[2]);
					enviarMensaje("actualizarRotacion#0#"+mensajeCompuesto[2], jugadores[0].ip, jugadores[0].puerto );
				}
			}
				
			default:
				break;
			}
		

			}
				
		
		
		public void enviarMensaje(String msg, InetAddress ipDestino, int puerto) { //Manda mensaje a un jugador solo
			byte[] mensaje = msg.getBytes();
			try {
				DatagramPacket dp = new DatagramPacket(mensaje, mensaje.length, ipDestino, puerto);
				socket.send(dp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void enviarMensaje(String msg) {//Manda mensaje a todos los clientes
			byte[] mensaje = msg.getBytes();
			try {
				for(int i = 0; i<jugadores.length;i++) {				
				DatagramPacket dp = new DatagramPacket(mensaje, mensaje.length, jugadores[i].ip, jugadores[i].puerto);
				socket.send(dp);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		public void fin() {
			fin = true;
			socket.close();
		}

	
}
