package is.ru.graphics.gameobjects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;

import is.ru.graphics.math.ModelMatrix;

public class GameCanvas extends GameObject{
	
	// Instantiation objects
	private DrawableGameObject mouse1_object;
	private DrawableGameObject mouse2_object;
	
	// Variables for the object being drawn
	private DrawableGameObject currentDraw;
	private float current_start_x;
	private float current_start_y;
	
	public GameCanvas(DrawableGameObject mouse1_object, DrawableGameObject mouse2_object) {
		this.mouse1_object = mouse1_object;
		this.mouse2_object = mouse2_object;
	}
	
	@Override
	public void update(float deltatime) {
		if(!Gdx.input.isTouched() && currentDraw != null) {
			currentDraw = null;
			return;
		}
		else if(Gdx.input.isTouched() && currentDraw == null) {
			DrawableGameObject instance = Gdx.input.isButtonPressed(Input.Buttons.LEFT) ? mouse1_object : mouse2_object;
			ModelMatrix m = new ModelMatrix();
			m.loadIdentityMatrix();
			currentDraw = (DrawableGameObject)instantiate(instance, m);
			current_start_x = Gdx.input.getX();
			current_start_y = Gdx.graphics.getHeight() - Gdx.input.getY();
			Vector3 coords = Camera.screenToWorld(new Vector3(current_start_x, current_start_y, 1.0f));
			current_start_x = coords.x;
			current_start_y = coords.y;
		}
		
		if(Gdx.input.isTouched()) {
			float current_mouse_x = Gdx.input.getX();
			float current_mouse_y = Gdx.graphics.getHeight() - Gdx.input.getY();
			Vector3 coords = Camera.screenToWorld(new Vector3(current_mouse_x, current_mouse_y, 1.0f));
			current_mouse_x = coords.x; current_mouse_y = coords.y;
			currentDraw.changeDiagonalCorners(current_start_x, current_start_y, current_mouse_x, current_mouse_y);
		}
	}

	@Override
	public void draw(int colorloc) {
		// Hidden object do not draw
	}

	@Override
	public void onCollision(Vector3 normal) {
		// TODO Auto-generated method stub
		
	}

}
