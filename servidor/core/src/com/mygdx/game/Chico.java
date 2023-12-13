package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Chico {
	
	private int veloPersonaje = 90;
	
	public Vector2 posicion;
	public int xInicial, yInicial;
	public float tiempoDeVida = 0, tiempoMaxDeVida=50;
	public boolean disparo = false;
	public float rotacion;
	public float posXBala, posYBala;
	public int id=-1;
	public int rangoDeGolpeo = 32;
	
	public Chico(int x, int y) {
		xInicial = x;
		yInicial = y;
		posicion = new Vector2 (xInicial,yInicial);
	}
	
	public void movPersonaje(String direccion) {
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		
	    boolean seMueve = false;

	    if (direccion.equals("izquierda")) 
	    {
	    	posicion.x -= veloPersonaje * deltaTime;
	        seMueve = true;
	        
	    } else if (direccion.equals("derecha")) {
	    	
	        posicion.x += veloPersonaje * deltaTime;
	        seMueve = true;
	    }

	    if (direccion.equals("arriba")) {
	    	
	    	  posicion.y += veloPersonaje * deltaTime;
	        seMueve = true;
	        
	    } else if (direccion.equals("abajo")) {
	    	
	    	  posicion.y -= veloPersonaje * deltaTime;
	        seMueve = true;
	    }

	}
	
//	public void golpear(Rectangle agredido) {
//		if(agredido.getPosition().x) {
//			
//		}
//	}
	public void calcularBala() {
        if (disparo) {
            if (tiempoDeVida < tiempoMaxDeVida) {
                if (tiempoDeVida == 0) {
                    posXBala = posicion.x;
                    posYBala = posicion.y;
                } else {
                    posXBala += MathUtils.cosDeg(rotacion) * 10;
                    posYBala += MathUtils.sinDeg(rotacion) * 10;
                }
                tiempoDeVida += Gdx.graphics.getDeltaTime(); // Ajuste aquí

                // Si el tiempo de vida alcanza el máximo, reiniciar valores
                if (tiempoDeVida >= tiempoMaxDeVida) {
                    tiempoDeVida = 0;
                    disparo = false;
                }
            }
        } else {
            // Si no hay disparo, reiniciar valores
            tiempoDeVida = 0;
        }

        // Enviar el mensaje solo si hay un cambio en el tiempo de vida o estado de disparo
        if (tiempoDeVida > 0) {
            UtilesRed.hs.enviarMensaje("balaDisparada#" + posXBala + "#" + posYBala + "#" + rotacion);
        }

        }
    }