package is.ru.graphics.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;

import is.ru.graphics.graphics.RectangleGraphics;
import is.ru.graphics.graphics.TriangleGraphics;
import is.ru.graphics.math.ModelMatrix;

public class Cannon extends GameObject {
	
	private GameObject shot = new CannonBall();
	private final float BARREL_LENGTH = 100f;
	private final float ROTATION_PER_SECOND = 45.0f;
	private final long FIRE_TIMEOUT_MILLIS = 50;
	
	private long lastFireTimeMillis = 0;
	
	public Cannon() {
		super();
		Vector3 point = new Vector3(Gdx.graphics.getWidth()/2.0f, 0, 0);
		Vector3 world = Camera.screenToWorld(point);
		transform.addTranslation(world.x, BARREL_LENGTH, 0);
	}
	
	@Override
	public void update(float deltatime) {
		
		if(System.currentTimeMillis() < lastFireTimeMillis + FIRE_TIMEOUT_MILLIS)
			return;
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
			fire();
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			transform.addRotationZ(-ROTATION_PER_SECOND * deltatime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			transform.addRotationZ(ROTATION_PER_SECOND * deltatime);
		}
	}

	@Override
	public void draw(int colorloc) {
		// Move origin
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTransformation(transform.matrix);
		
		// DRAW Cannon wood piece
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(0, -BARREL_LENGTH/4.0f, 0);
		ModelMatrix.main.addScale(40f, 40f, 1);
		ModelMatrix.main.setShaderMatrix();
		Gdx.gl.glUniform4f(colorloc, 0.7f, 0.3f, 0.3f, 1);
		RectangleGraphics.drawSolidSquare();
		ModelMatrix.main.popMatrix();
		
		// Draw cannon barrel
		ModelMatrix.main.addScale(20, BARREL_LENGTH, 1);
		ModelMatrix.main.setShaderMatrix();
		Gdx.gl.glUniform4f(colorloc, 0.6f, 0.6f, 0.6f, 1);
		RectangleGraphics.drawSolidSquare();
		ModelMatrix.main.popMatrix();
	}
	
	private void fire() {
		lastFireTimeMillis = System.currentTimeMillis();
		transform.pushMatrix();
		ModelMatrix t = new ModelMatrix();
		t.loadIdentityMatrix();
		t.addToBaseCoords(0, BARREL_LENGTH/2.0f, 0);
		transform.addTransformation(t.matrix);
		instantiate(shot, transform);
		transform.popMatrix();
	}

}
