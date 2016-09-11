package is.ru.graphics.gameobjects;

import com.badlogic.gdx.math.Vector2;

import is.ru.graphics.CannonBallGame;
import is.ru.graphics.math.ModelMatrix;

/**
 * The super type that every object in the game
 * needs to extend. 
 * @author Sverrir
 */
public abstract class GameObject {
	
	protected ModelMatrix transform;
	
	public GameObject() {
		transform = new ModelMatrix();
	}
	
	/**
	 * Adds the given object to the game world.
	 * 
	 * @param object	The object being instantiated in the game
	 */
	protected final void instantiate(GameObject object) {
		CannonBallGame.getInstance().addGameObject(object);
	}
	
	/**
	 * Removes the given object from the game world.
	 */
	protected final void destroy() {
		CannonBallGame.getInstance().removeGameObject(this);
	}
	
	public abstract void update(float deltatime);
	public abstract void draw();
	
}
