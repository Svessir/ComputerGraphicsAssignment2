package is.ru.graphics.gameobjects;

import com.badlogic.gdx.math.Vector3;

import is.ru.graphics.graphics.CircleGraphics;
import is.ru.graphics.graphics.RectangleGraphics;
import is.ru.graphics.math.ModelMatrix;

public class CannonBall extends GameObject {
	
	private Vector3 velocity;
	private float speed = 10.0f;
	
	public CannonBall() {
		Vector3 forward = transform.forward();
	}
	
	@Override
	public void update(float deltatime) {
		Vector3 forward = transform.forward();
		velocity = new Vector3(forward.x * speed, forward.y * speed, 0);
		this.transform.addToBaseCoords(velocity.x * deltatime, velocity.y * deltatime, 0);
	}

	@Override
	public void draw(int colorloc) {
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTransformation(transform.matrix);
		ModelMatrix.main.addScale(0.5f, 0.5f, 0);
		ModelMatrix.main.setShaderMatrix();
		CircleGraphics.drawSolidCircle();
		//RectangleGraphics.drawSolidSquare();
		ModelMatrix.main.popMatrix();
	}

	@Override
	public void onCollision(Vector3 normal) {
		// TODO Auto-generated method stub
		
	}
	
}
