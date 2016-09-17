package is.ru.graphics.math;

import com.badlogic.gdx.math.Vector3;

public class CollisionEdge {
	public final Vector3 point1;
	public final Vector3 point2;
	public final Vector3 normal;
	
	public CollisionEdge(Vector3 point1, Vector3 point2) {
		this.point1 = point1;
		this.point2 = point2;
		this.normal = new Vector3(-(this.point2.y - this.point1.y), this.point2.x - this.point1.x, 0.0f);
		float length = normal.len();
		//this.normal.x /= length;
		//this.normal.y /= length;
		
		Vector3 v = new Vector3(point2.x - point1.x, point2.y - point1.y, 0);
		this.normal.nor();
		if(!this.normal.isUnit())
			System.out.println(this.normal.len());
		//if(v.dot(normal) != 0)
			//System.out.println(v.dot(normal));
	}
	
	public Collision getCollision(Vector3 vertex, Vector3 velocity, float deltatime) {
		float time_hit = normal.dot(new Vector3(point1.x - vertex.x, point1.y - vertex.y, 0)) 
				/ normal.dot(velocity);
		
		float col_x = vertex.x + velocity.x * time_hit;
		float col_y = vertex.y + velocity.y * time_hit;
		
		//if(time_hit >= 0)
			//System.out.println(time_hit + " " + deltatime);
		
		if(Float.isNaN(time_hit) || Float.isInfinite(time_hit) || time_hit < 0 || time_hit > deltatime ||
				col_x < Math.min(point1.x, point2.x) || col_x > Math.max(point1.x, point2.x) || 
				col_y < Math.min(point1.y, point2.y) || col_y > Math.max(point1.y, point2.y))
			return null;
		
		return new Collision(normal, time_hit);
	}
}
