package is.ru.graphics.gameobjects;

import com.badlogic.gdx.math.Vector3;

import is.ru.graphics.graphics.RectangleGraphics;
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
	public void onCollision(Vector3 normal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeDiagonalCorners(float point1_x, float point1_y, float point2_x, float point2_y) {
		float left = Math.min(point1_x, point2_x);
		float right = Math.max(point1_x, point2_x);
		float bottom = Math.min(point1_y, point2_y);
		float top = Math.max(point1_y, point2_y);
		
		width = right - left;
		height = top - bottom;
		
		// Calculate new centers
		float center_x = left + width/2.0f;
		float center_y = bottom + height/2.0f;
		
		// Reset transformation
		transform.loadIdentityMatrix();
		
		// Move transform to center coordinates
		transform.addToBaseCoords(center_x, center_y, 0);
		transform.print();
	}

}
