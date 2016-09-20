package is.ru.graphics.gameobjects;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector3;

import is.ru.graphics.CannonBallGame;
import is.ru.graphics.graphics.TriangleGraphics;
import is.ru.graphics.math.CollisionEdge;
import is.ru.graphics.math.ModelMatrix;

public class Target extends GameObject {
	private Vector3 point1;
	private Vector3 point2;
	private Vector3 point3;
	private float sideLength = 50f;
	
	public Target(Vector3 center) {
		transform.addTranslation(center.x, center.y, 0);
		
		// Calculate points for equilateral triangle
		float sidelength_half = sideLength/2.0f;
		float a = (float)Math.sqrt(Math.pow(sideLength, 2) - Math.pow(sidelength_half, 2));
		float rad = (2.0f * a) / 3.0f;
		float rad_half = a/3.0f;
		point1 = new Vector3(center.x, center.y + rad, 1);
		point2 = new Vector3(center.x + sidelength_half, center.y - rad_half, 1);
		point3 = new Vector3(center.x - sidelength_half, center.y - rad_half, 1);
	}
	
	@Override
	public void update(float deltatime) {
	}

	@Override
	public void draw(int coslorloc) {
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.setShaderMatrix();
		TriangleGraphics.drawSolidTriangle(point1.x, point1.y, point2.x, point2.y, point3.x, point3.y);
		ModelMatrix.main.popMatrix();
	}
	
	@Override
	public ArrayList<CollisionEdge> getCollisionEdges() {
		ArrayList<CollisionEdge> edges = new ArrayList<CollisionEdge>();
		edges.add(new CollisionEdge(point1, point2));
		edges.add(new CollisionEdge(point1, point3));
		edges.add(new CollisionEdge(point2, point3));
		return edges;
	}
	
	@Override
	public void onTouch(GameObject object) {
		object.destroy();
		CannonBallGame.getInstance().endLevel();
	}

}
