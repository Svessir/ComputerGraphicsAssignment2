package is.ru.graphics.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.FloatBuffer;

public class CircleGraphics {
    private static int verticesPerCircle = 100;
    float[] array = new float[2*verticesPerCircle];
    private static FloatBuffer vertexBuffer;
    private static int vertexPointer;

    public static void create(int vertexPointer){
        CircleGraphics.vertexPointer = vertexPointer;
        float it = 0.0f;
        vertexBuffer = BufferUtils.newFloatBuffer(2*verticesPerCircle);
        for(int i = 0; i < verticesPerCircle; i++){
            vertexBuffer.put((float)Math.cos(it));
            vertexBuffer.put((float)Math.sin(it));
            it += (2 * Math.PI) /(double) verticesPerCircle;
        }
        vertexBuffer.rewind();
    }

    public static void drawSolidCircle(){
        Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT, false, 0, vertexBuffer);
        Gdx.gl.glDrawArrays(GL20.GL_TRIANGLE_FAN, 0, verticesPerCircle);

    }
}
