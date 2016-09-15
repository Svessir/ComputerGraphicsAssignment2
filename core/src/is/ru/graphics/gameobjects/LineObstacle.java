package is.ru.graphics.gameobjects;

import com.badlogic.gdx.math.Vector3;

import is.ru.graphics.graphics.LineGraphics;
import is.ru.graphics.math.ModelMatrix;

public class LineObstacle extends DrawableGameObject {
	
	private Vector3 point1;
	private Vector3 point2;
	
	public LineObstacle() {
		point1 = new Vector3(0,0,1);
		point2 = new Vector3(0,0,1);
	}
	
	@Override
	public void update(float deltatime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(int colorloc) {
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.setShaderMatrix();
		LineGraphics.drawLine(point1.x, point1.y, point2.x, point2.y);
		ModelMatrix.main.popMatrix();
	}

	@Override
	public void onCollision(Vector3 normal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeDiagonalCorners(float point1_x, float point1_y, float point2_x, float point2_y) {
		point1.x = point1_x;
		point1.y = point1_y;
		point2.x = point2_x;
		point2.y = point2_y;
		
	}

}
