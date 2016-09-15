package is.ru.graphics.graphics;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

public class LineGraphics {
	private static FloatBuffer vertexBuffer;
	private static int vertexPointer;
	
	public static void create(int vertexPointer) {
		LineGraphics.vertexPointer = vertexPointer;
		vertexBuffer = BufferUtils.newFloatBuffer(4);
	}
	
	public static void drawLine(float point1_x, float point1_y, float point2_x, float point2_y) {
		float[] array = {point1_x,point1_y,
						point2_x,point2_y,};
		vertexBuffer.put(array);
		vertexBuffer.rewind();
		Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT, false, 0, vertexBuffer);
		Gdx.gl.glDrawArrays(GL20.GL_LINES, 0, 2);
	}
}
