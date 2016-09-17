package is.ru.graphics.math;

import com.badlogic.gdx.math.Vector3;

/**
 * Wrapper object for collision information
 * @author Sverrir
 *
 */
public class Collision implements Comparable<Collision>{
	
	public final Vector3 colliderNormal;
	public final float timeToCollision;
	
	public Collision(Vector3 colliderNormal, float timeToCollision) {
		this.colliderNormal = colliderNormal;
		this.timeToCollision = timeToCollision;
	}

	@Override
	public int compareTo(Collision o) {
		if(o == null || this.timeToCollision < o.timeToCollision)
			return 1;
		else if(this.timeToCollision > o.timeToCollision)
			return -1;
		return 0;
	}
	
	public String toString() {
		return timeToCollision + "";
	}
}
