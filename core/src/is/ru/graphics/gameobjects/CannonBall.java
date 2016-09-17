package is.ru.graphics.gameobjects;

import com.badlogic.gdx.math.Vector3;

import is.ru.graphics.graphics.CircleGraphics;
import is.ru.graphics.graphics.LineGraphics;
import is.ru.graphics.math.CollisionEdge;
import is.ru.graphics.math.CollisionVertex;
import is.ru.graphics.math.ModelMatrix;

public class CannonBall extends GameObject {
	
	private Vector3 velocity;
	private CollisionEdge e;
	
	public CannonBall() {
		speed = 260.0f;
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
		ModelMatrix.main.addScale(5f, 5f, 0);
		ModelMatrix.main.setShaderMatrix();
		CircleGraphics.drawSolidCircle();
		ModelMatrix.main.popMatrix();
		
		if(e != null) {
			ModelMatrix.main.pushMatrix();
			ModelMatrix.main.setShaderMatrix();
			LineGraphics.drawLine(e.point1.x, e.point1.y, e.point2.x, e.point2.y);
			ModelMatrix.main.popMatrix();
		}
	}
	
	@Override
	public void onCollision(Vector3 colliderNormal) {
		Vector3 forward = transform.forward();
		forward.z = 0;
		float b = 2 * forward.dot(colliderNormal);
		Vector3 c = new Vector3(b * colliderNormal.x, b * colliderNormal.y, 0);
		Vector3 reflection = new Vector3(forward.x - c.x, forward.y - c.y, 0);
		
		float cosTheta = (forward.dot(reflection) / (forward.len() * reflection.len()));
		float angle = (float)(Math.acos(cosTheta) * 180.0/Math.PI);
		
		//transform.pushMatrix();
		transform.addRotationZ(angle);
		/*
		if(transform.forward().hasSameDirection(reflection)) {
			transform.popMatrix();
			transform.addRotationZ(angle);
		}
		else {
			transform.popMatrix();
			transform.addRotationZ(-angle);
		}*/
		
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.setShaderMatrix();
		e = new CollisionEdge(new Vector3(transform.origin().x, transform.origin().y, 0), new Vector3(transform.origin().x + reflection.x * 20, transform.origin().y + reflection.y * 20, 0));
		ModelMatrix.main.popMatrix();
	}
	
	@Override
	public CollisionVertex getCollisionVertex() {
		Vector3 velocity = transform.forward();
		velocity.x *= speed;
		velocity.y *= speed;
		
		return new CollisionVertex(transform.origin(), velocity);
	}
}
