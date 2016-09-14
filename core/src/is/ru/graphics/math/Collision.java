package is.ru.graphics.math;

import com.badlogic.gdx.math.Vector3;

/**
 * Wrapper object for collision information
 * @author Sverrir
 *
 */
public class Collision {
	public Collision() {
	}
	
	public Collision(Vector3 colliderNormal, float timeSinceCollision) {
		this.colliderNormal = colliderNormal;
		this.timeSinceCollision = timeSinceCollision;
	}
	
	public Vector3 colliderNormal;
	public float timeSinceCollision;
}
