package com.mygdx.game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class PantallaJuego implements Screen{

	private Chico chico1, chico2; 
	
//creo una entidad zombie. 
	
	private Entidad zombie;
	public static Servidor servidor;
	
	 @Override
	public void show() {
	
		 servidor = new Servidor(this);
		 
		 //Creamos al personaje y lo instanciamos.
		 chico1 = new Chico(64,64);
		 chico2 = new Chico(32*6,32*6);
		 //creo e instancio el zombie
		 zombie = new Zombie();
		 
	}

	@Override
	 public void render(float delta) {
		if(UtilesRed.hs.empezo) {
			
		chico1.calcularBala();
		chico2.calcularBala();
		}
	}

	 

	@Override
    public void resize(int width, int height) {


	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		servidor.cerrarHilo();//Cierra el hilo asi no me queda abierto en segundo plano

	}
	
	
	public Chico getChico1() {
		return chico1;
	}
	
	public Chico getChico2() {
		return chico2;
	}
	

}
