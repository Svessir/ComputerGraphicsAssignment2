package is.ru.graphics.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.Buffer;
import java.nio.FloatBuffer;

/**
 * Created by Jóna on 17.9.2016.
 */
public class TriangleGraphics {
    private static FloatBuffer vertexBuffer;
    private static int vertexPointer;

    public static void create(int vertexPointer){
        TriangleGraphics.vertexPointer = vertexPointer;

        float[] array = {-2.0f, -2.0f,
                2.0f, -2.0f,
                0.0f,  2.0f};
        vertexBuffer = BufferUtils.newFloatBuffer(6);
        vertexBuffer.put(array);
        vertexBuffer.rewind();
    }

    public static void drawSolidTriangle(){
        Gdx.gl.glVertexAttribPointer(vertexPointer, 2, GL20.GL_FLOAT, false, 0, vertexBuffer);
        Gdx.gl.glDrawArrays(GL20.GL_TRIANGLES, 0, 3);
    }
}
