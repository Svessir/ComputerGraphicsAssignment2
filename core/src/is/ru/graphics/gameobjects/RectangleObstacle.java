package is.ru.graphics.gameobjects;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

import is.ru.graphics.graphics.RectangleGraphics;
import is.ru.graphics.math.CollisionEdge;
import is.ru.graphics.math.ModelMatrix;

public class RectangleObstacle extends DrawableGameObject {
	
	private float width;
	private float height;
	
	public RectangleObstacle() {
		
	}
	
	public RectangleObstacle(float width, float height, float centerX, float centerY) {
		super();
		this.width = width;
		this.height = height;
		transform.addToBaseCoords(centerX, centerY, 0);
	}
	
	@Override
	public void update(float deltatime) {
		
	}

	@Override
	public void draw(int colorloc) {
		ModelMatrix.main.pushMatrix();
        Gdx.gl.glUniform4f(colorloc, 1f, 1f, 1f, 1);
        ModelMatrix.main.addTransformation(transform.matrix);
		ModelMatrix.main.addScale(width, height, 1);
		ModelMatrix.main.setShaderMatrix();
		RectangleGraphics.drawSolidSquare();
		ModelMatrix.main.popMatrix();
	}

	@Override
	public void draw(float drawStart_x, float drawStart_y, float drawEnd_x, float drawEnd_y) {
		float left = Math.min(drawStart_x, drawEnd_x);
		float right = Math.max(drawStart_x, drawEnd_x);
		float bottom = Math.min(drawStart_y, drawEnd_y);
		float top = Math.max(drawStart_y, drawEnd_y);
		
		width = right - left;
		height = top - bottom;
		
		// Calculate new center
		float center_x = left + width/2.0f;
		float center_y = bottom + height/2.0f;
		
		// Reset transformation
		transform.loadIdentityMatrix();
		
		// Move transform to center coordinates
		transform.addToBaseCoords(center_x, center_y, 0);
	}
	
	@Override
	public ArrayList<CollisionEdge> getCollisionEdges() {
		ArrayList<CollisionEdge> list = new ArrayList<CollisionEdge>();
		Vector3 origin = transform.origin();
		float half_width = width/2.0f;
		float half_height = height/2.0f;
		Vector3 bottom_left = new Vector3(origin.x - half_width, origin.y - half_height, 1);
		Vector3 top_left = new Vector3(origin.x - half_width, origin.y + half_height, 1);
		Vector3 bottom_right = new Vector3(origin.x + half_width, origin.y - half_height, 1);
		Vector3 top_right = new Vector3(origin.x + half_width, origin.y + half_height, 1);
		list.add(new CollisionEdge(bottom_left, top_left));
		list.add(new CollisionEdge(bottom_left,bottom_right));
		list.add(new CollisionEdge(bottom_right, top_right));
		list.add(new CollisionEdge(top_right, top_left));
		return list;
	}

}
