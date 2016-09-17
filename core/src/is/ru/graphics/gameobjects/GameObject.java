package is.ru.graphics.gameobjects;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector3;

import is.ru.graphics.CannonBallGame;
import is.ru.graphics.math.Collision;
import is.ru.graphics.math.CollisionEdge;
import is.ru.graphics.math.CollisionVertex;
import is.ru.graphics.math.ModelMatrix;

/**
 * The super type that every object in the game
 * needs to extend. 
 * @author Sverrir
 */
public abstract class GameObject {
	
	protected ModelMatrix transform;
	protected float speed = 0.0f;
	
	public GameObject() {
		transform = new ModelMatrix();
	}
	
	/**
	 * Adds the given object to the game world.
	 * 
	 * @param object	The object being instantiated in the game
	 * @param transform	The transformation of the object
	 */
	protected final GameObject instantiate(GameObject object, ModelMatrix transform) {
		try {
			GameObject newObj = object.getClass().newInstance();
			newObj.setTransform(transform);
			CannonBallGame.getInstance().addGameObject(newObj);
			newObj.create();
			return newObj;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Removes the given object from the game world.
	 */
	public final void destroy() {
		CannonBallGame.getInstance().removeGameObject(this);
	}
	
	public void setTransform(ModelMatrix transform) {
		ModelMatrix cpy = new ModelMatrix();
		cpy.matrix = transform.copy();
		this.transform = cpy;
	}
	
	public final ModelMatrix getTransform() {
		ModelMatrix modelMatrix = new ModelMatrix();
		modelMatrix.matrix.put(transform.copy());
		return modelMatrix;
	}
	
	public ArrayList<CollisionEdge> getCollisionEdges() {
		return new ArrayList<CollisionEdge>();
	}
	
	public CollisionVertex getCollisionVertex() {
		return null;
	}
	
	public void onCollision(Vector3 colliderNormal) {
	}
	
	public void create() {
	}
	
	public abstract void update(float deltatime);
	public abstract void draw(int colorloc);
	
}
