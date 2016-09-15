package is.ru.graphics.gameobjects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;

import is.ru.graphics.math.ModelMatrix;

/**
 * Game component that listens to mouse events and
 * draws given drawable game objects to the screen
 * depending on the mouse event.
 * 
 * @author Sverrir
 */
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
		// If mouse button is not touched and there is a tracked object
		// Then stop drawing by releasing the object
		if(!Gdx.input.isTouched() && currentDraw != null) {
			currentDraw = null;
			return;
		}
		// If a mouse button is touched and we don't have a tracked object
		// Then initialize a new object to be tracked
		else if(Gdx.input.isTouched() && currentDraw == null) {
			
			// Get the type of object depending on the mouse button that was clicked
			DrawableGameObject instance = Gdx.input.isButtonPressed(Input.Buttons.LEFT) ? mouse1_object : mouse2_object;
			
			// Instantiate the object
			ModelMatrix m = new ModelMatrix();
			m.loadIdentityMatrix();
			currentDraw = (DrawableGameObject)instantiate(instance, m);
			
			// Get the starting draw point and convert it to world coordinates
			current_start_x = Gdx.input.getX();
			current_start_y = Gdx.graphics.getHeight() - Gdx.input.getY();
			Vector3 coords = Camera.screenToWorld(new Vector3(current_start_x, current_start_y, 1.0f));
			current_start_x = coords.x;
			current_start_y = coords.y;
			
		}
		
		// If a mouse button is touched then update the tracked object
		if(Gdx.input.isTouched()) {
			// Get the mouse viewport coordinates and convert them to world coordinates
			float current_mouse_x = Gdx.input.getX();
			float current_mouse_y = Gdx.graphics.getHeight() - Gdx.input.getY();
			Vector3 coords = Camera.screenToWorld(new Vector3(current_mouse_x, current_mouse_y, 1.0f));
			current_mouse_x = coords.x; current_mouse_y = coords.y;
			
			// Update the object according to the start drawing point and the mouse position point
			currentDraw.draw(current_start_x, current_start_y, current_mouse_x, current_mouse_y);
		}
	}

	@Override
	public void draw(int colorloc) {
		// Hidden object: No need to draw.
	}

}
