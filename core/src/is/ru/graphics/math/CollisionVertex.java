package is.ru.graphics.math;

import com.badlogic.gdx.math.Vector3;

public class CollisionVertex {
	public final Vector3 vertex;
	public final Vector3 velocity;
	
	public CollisionVertex(Vector3 vertex, Vector3 velocity) {
		this.vertex = vertex;
		this.velocity = velocity;
	}
}
