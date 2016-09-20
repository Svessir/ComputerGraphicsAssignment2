package is.ru.graphics.gameobjects;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector3;

import is.ru.graphics.math.CollisionEdge;

public class Boundary extends GameObject{
	
	private ArrayList<CollisionEdge> edges = new ArrayList<CollisionEdge>();
	
	public Boundary(float left, float right, float bottom, float top) {
		Vector3 point1 = new Vector3(left, bottom, 1);
		Vector3 point2 = new Vector3(left, top, 1);
		Vector3 point3 = new Vector3(right, bottom, 1);
		Vector3 point4 = new Vector3(right, top, 1);
		edges.add(new CollisionEdge(point1, point2));
		edges.add(new CollisionEdge(point1, point3));
		edges.add(new CollisionEdge(point3, point4));
		edges.add(new CollisionEdge(point2, point4));
	}
	
	@Override
	public void update(float deltatime) {
	}

	@Override
	public void draw(int colorloc) {
	}
	
	@Override
	public ArrayList<CollisionEdge> getCollisionEdges() {
		return edges;
	}
	
	@Override
	public void onTouch(GameObject object) {
		object.destroy();
	}
}
