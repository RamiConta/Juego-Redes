package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;


public abstract class Entidad {
	
	private int vida = 100;
	private float velocidad = 20;
	private int daño = 20;
	private Vector2 posicion;
	
	float movimientoX, movimientoY;
	
	
	public Entidad() {
		
		posicion = new Vector2(32*4,32*4);
		
		
	}
	
	public void update (Vector2 posicionChico) {	
		mirar(posicionChico);
	}
	
	protected void mirar(Vector2 posicionChico) {
			float delta = Gdx.graphics.getDeltaTime();
		
		    float posChicoX = (posicionChico.x-32) - posicion.x; //le resto 32 ya que sino, la entidad mira al centro del sprite sheet en su totalidad (64*4 pixeles) cuando solo quiero que apunte a un solo frame
		    float posChicoY = (posicionChico.y-32) - posicion.y;

		    float anguloRadianes = MathUtils.atan2(posChicoY, posChicoX);
		    float anguloEnGrados = MathUtils.radiansToDegrees * anguloRadianes;

		    // Ajuste para que el ángulo sea positivo y esté en el rango correcto (0 a 360 grados)
		    anguloEnGrados = (anguloEnGrados + 360) % 360;


		    
		    if(posicion.x > posicionChico.x-32) {
		    	posicion.x-= velocidad *delta; 
		    }else if(posicion.x < posicionChico.x-32) {
		    	posicion.x+= velocidad *delta;
		    }
		    
		    if(posicion.y > posicionChico.y-32){
		    posicion.y-= velocidad *delta;
		    }else if(posicion.y < posicionChico.y-32) {
		    	posicion.y+= velocidad *delta;
		    }
		    
		
	}
	
	protected void mirar(Vector2 posicionChico, float angulo) {
		float delta = Gdx.graphics.getDeltaTime();
	
	    
		movimientoX = MathUtils.cosDeg(angulo);
		movimientoY = MathUtils.sinDeg(angulo);
		
		posicion.x = posicionChico.x - 32;
		posicion.y = posicionChico.y - 32;
}
	
	protected void moverBala(Vector2 posicionChico) {
		
		posicion.x += movimientoX*10;
		posicion.y += movimientoY*10;
		
	}
	

	
	
}
