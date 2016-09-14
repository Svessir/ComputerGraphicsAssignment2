package is.ru.graphics.gameobjects;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.BufferUtils;

public class Camera {
	
	private static int projectionMatrixLoc;
	private static FloatBuffer projectionMatrix = BufferUtils.newFloatBuffer(16);
	private static float left, right, bottom, top;
	
	public static void OrthographicProjection2D(float left, float right, float bottom, float top) {
		float[] pm = new float[16];

		pm[0] = 2.0f / (right - left); pm[4] = 0.0f; pm[8] = 0.0f; pm[12] = -(right + left)/(right - left);
		pm[1] = 0.0f; pm[5] = 2.0f / (top - bottom); pm[9] = 0.0f; pm[13] = -(top + bottom)/(top - bottom);
		pm[2] = 0.0f; pm[6] = 0.0f; pm[10] = 1.0f; pm[14] = 0.0f;
		pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = 0.0f; pm[15] = 1.0f;

		Camera.projectionMatrix = BufferUtils.newFloatBuffer(16);
		Camera.projectionMatrix.put(pm);
		Camera.projectionMatrix.rewind();
		
		Camera.left = left; Camera.right = right; Camera.bottom = bottom; Camera.top = top;
	}
	
	public static void setProjectionMatrix(int projectionMatrixLoc) {
		Camera.projectionMatrixLoc = projectionMatrixLoc;
		Camera.setProjectionMatrix();
	}
	
	public static void setProjectionMatrix() {
		Gdx.gl.glUniformMatrix4fv(Camera.projectionMatrixLoc, 1, false, Camera.projectionMatrix);
	}
	
	public static Vector3 screenToWorld(Vector3 point) {
		final float a = (Camera.right - Camera.left) / Gdx.graphics.getWidth();
		final float b = (Camera.top - Camera.bottom) / Gdx.graphics.getHeight();
		final float c = Camera.left;
		final float d = Camera.bottom;
		
		Vector3 s = new Vector3();
		s.x = a * point.x + c;
		s.y = b * point.y + d;
		s.z = 1;
		return s;
	}
}
