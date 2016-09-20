package is.ru.graphics.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

import is.ru.graphics.graphics.CircleGraphics;
import is.ru.graphics.math.CollisionVertex;
import is.ru.graphics.math.ModelMatrix;

public class CannonBall extends GameObject {
	
	private Vector3 velocity;
	private Vector3 forward;
	private Vector3 position;

	@Override
	public void create() {
		this.speed = 260f;
		this.forward = transform.forward();
		this.position = transform.origin();
	}
	
	@Override
	public void update(float deltatime) {
		velocity = new Vector3(forward.x * speed, forward.y * speed, 0);
		this.position.x += velocity.x * deltatime;
		this.position.y += velocity.y * deltatime;
	}

	@Override
	public void draw(int colorloc) {
		ModelMatrix.main.pushMatrix();
		Gdx.gl.glUniform4f(colorloc, 0.6f, 0.6f, 0.6f, 1);
		ModelMatrix.main.addTranslation(position.x, position.y, 0);
		ModelMatrix.main.addScale(5f, 5f, 0);
		ModelMatrix.main.setShaderMatrix();
		CircleGraphics.drawSolidCircle();
		ModelMatrix.main.popMatrix();
	}
	
	@Override
	public void onCollision(Vector3 colliderNormal) {
		float b = 2 * forward.dot(colliderNormal);
		Vector3 c = new Vector3(b * colliderNormal.x, b * colliderNormal.y, 0);
		forward = new Vector3(forward.x - c.x, forward.y - c.y, 0);
	}
	
	@Override
	public CollisionVertex getCollisionVertex() {
		Vector3 velocity = new Vector3(forward);
		velocity.x *= speed;
		velocity.y *= speed;
		
		return new CollisionVertex(new Vector3(position), velocity);
	}
}
