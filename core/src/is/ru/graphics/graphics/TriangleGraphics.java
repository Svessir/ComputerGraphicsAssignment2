package is.ru.graphics.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.FloatBuffer;

public class TriangleGraphics {
    private static FloatBuffer vertexBuffer;
    private static int vertexPointer;

    public static void create(int vertexPointer){
        TriangleGraphics.vertexPointer = vertexPointer;
        vertexBuffer = BufferUtils.newFloatBuffer(6);
    }

    public static void drawSolidTriangle(float point1_x, float point1_y, float point2_x, 
    		float point2_y, float point3_x, float point3_y){
    	
    	float[] array = {point1_x, point1_y,
    			point2_x, point2_y,
    			point3_x,  point3_y};
    	vertexBuffer.put(array);
        vertexBuffer.rewind();
        
        Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT, false, 0, vertexBuffer);
        Gdx.gl.glDrawArrays(GL20.GL_TRIANGLES, 0, 3);
    }
}
