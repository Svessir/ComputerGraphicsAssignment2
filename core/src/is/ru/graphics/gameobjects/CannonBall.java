package is.ru.graphics.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;

import is.ru.graphics.graphics.RectangleGraphics;
import is.ru.graphics.math.ModelMatrix;

public class CannonBall extends GameObject {
	
	private Vector3 velocity;
	private float speed = 10.0f;
	
	public CannonBall() {
		velocity = new Vector3(0, 0, 0);
	}
	
	@Override
	public void update(float deltatime) {
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			transform.addRotationZ(-180.0f * deltatime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			transform.addRotationZ(180.0f * deltatime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			Vector3 forward = transform.forward();
			
			velocity.x = forward.x * deltatime * speed;
			velocity.y = forward.y * deltatime * speed;
		}
		
		this.transform.addToBaseCoords(velocity.x * deltatime, velocity.y * deltatime, 0);
	}

	@Override
	public void draw() {
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTransformation(transform.matrix);
		ModelMatrix.main.setShaderMatrix();
		RectangleGraphics.drawSolidSquare();
		ModelMatrix.main.popMatrix();
	}
	
}
