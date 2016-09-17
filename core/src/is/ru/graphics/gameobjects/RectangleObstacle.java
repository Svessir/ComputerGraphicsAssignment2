package is.ru.graphics.gameobjects;

import java.util.ArrayList;

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
		return super.getCollisionEdges();
	}

}
