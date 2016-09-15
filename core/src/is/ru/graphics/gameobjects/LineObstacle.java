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
	public void draw(float drawStart_x, float drawStart_y, float drawEnd_x, float drawEnd_y) {
		point1.x = drawStart_x;
		point1.y = drawStart_y;
		point2.x = drawEnd_x;
		point2.y = drawEnd_y;
		
	}

}
