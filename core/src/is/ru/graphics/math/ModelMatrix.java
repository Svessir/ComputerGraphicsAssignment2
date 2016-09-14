package is.ru.graphics.math;

import java.nio.FloatBuffer;
import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.BufferUtils;

public class ModelMatrix {
	
	public static ModelMatrix main = new ModelMatrix();
	
	public FloatBuffer matrix;
	
	private Stack<FloatBuffer> matrixStack;
	private int shaderMatrixPointer;
	private float[] Mtmp;
	
	public ModelMatrix() {
		matrix = BufferUtils.newFloatBuffer(16);
		matrixStack = new Stack<FloatBuffer>();
		Mtmp = new float[16];
		loadIdentityMatrix();
	}

	public void loadIdentityMatrix() {

		// m0  m4  m8  m12
		// m1  m5  m9  m13
		// m2  m6  m10  m14
		// m3  m7  m11  m15

		matrix.put(0, 1.0f);
		matrix.put(1, 0.0f);
		matrix.put(2, 0.0f);
		matrix.put(3, 0.0f);
		matrix.put(4, 0.0f);
		matrix.put(5, 1.0f);
		matrix.put(6, 0.0f);
		matrix.put(7, 0.0f);
		matrix.put(8, 0.0f);
		matrix.put(9, 0.0f);
		matrix.put(10, 1.0f);
		matrix.put(11, 0.0f);
		matrix.put(12, 0.0f);
		matrix.put(13, 0.0f);
		matrix.put(14, 0.0f);
		matrix.put(15, 1.0f);
	}
	
	public void addTranslation(float x, float y, float z) {
		matrix.put(12, matrix.get(0) * x + matrix.get(4) * y + matrix.get(8) * z + matrix.get(12));
		matrix.put(13, matrix.get(1) * x + matrix.get(5) * y + matrix.get(9) * z + matrix.get(13));
		matrix.put(14, matrix.get(2) * x + matrix.get(6) * y + matrix.get(10) * z + matrix.get(14));
	}
	
	public void addScale(float x, float y, float z) {
		matrix.put(0, matrix.get(0) * x);
		matrix.put(1, matrix.get(1) * x);
		matrix.put(2, matrix.get(2) * x);
		
		matrix.put(4, matrix.get(4) * y);
		matrix.put(5, matrix.get(5) * y);
		matrix.put(6, matrix.get(6) * y);
		
		matrix.put(8, matrix.get(8) * z);
		matrix.put(9, matrix.get(9) * z);
		matrix.put(10, matrix.get(10) * z);
	}
	
	public void addRotationZ(float angle) {
		float cos =(float)Math.cos((double)angle * Math.PI / 180.0f);
		float sin =(float)Math.sin((double)angle * Math.PI / 180.0f);
		
		Mtmp[0] = cos; Mtmp[4] = -sin; Mtmp[8] = 0; Mtmp[12] = 0;
		Mtmp[1] = sin; Mtmp[5] = cos; Mtmp[9] = 0; Mtmp[13] = 0;
		Mtmp[2] = 0; Mtmp[6] = 0; Mtmp[10] = 1; Mtmp[14] = 0;
		Mtmp[3] = 0; Mtmp[7] = 0; Mtmp[11] = 0; Mtmp[15] = 1;
		
		FloatBuffer buffer = BufferUtils.newFloatBuffer(16);
		buffer.put(Mtmp);
		this.addTransformation(buffer);
	}
	
	public void addToBaseCoords(float x, float y, float z) {
		this.matrix.put(12, this.matrix.get(12) + x);
		this.matrix.put(13, this.matrix.get(13) + y);
		this.matrix.put(14, this.matrix.get(14) + z);
	}

	public void addTransformation(FloatBuffer M2) {
		this.Mtmp[0] = matrix.get(0)*M2.get(0) + matrix.get(4)*M2.get(1) + matrix.get(8)*M2.get(2) + matrix.get(12)*M2.get(3);
		this.Mtmp[1] = matrix.get(1)*M2.get(0) + matrix.get(5)*M2.get(1) + matrix.get(9)*M2.get(2) + matrix.get(13)*M2.get(3);
		this.Mtmp[2] = matrix.get(2)*M2.get(0) + matrix.get(6)*M2.get(1) + matrix.get(10)*M2.get(2) + matrix.get(14)*M2.get(3);
		this.Mtmp[3] = matrix.get(3)*M2.get(0) + matrix.get(7)*M2.get(1) + matrix.get(11)*M2.get(2) + matrix.get(15)*M2.get(3);
		this.Mtmp[4] = matrix.get(0)*M2.get(4) + matrix.get(4)*M2.get(5) + matrix.get(8)*M2.get(6) + matrix.get(12)*M2.get(7);
		this.Mtmp[5] = matrix.get(1)*M2.get(4) + matrix.get(5)*M2.get(5) + matrix.get(9)*M2.get(6) + matrix.get(13)*M2.get(7);
		this.Mtmp[6] = matrix.get(2)*M2.get(4) + matrix.get(6)*M2.get(5) + matrix.get(10)*M2.get(6) + matrix.get(14)*M2.get(7);
		this.Mtmp[7] = matrix.get(3)*M2.get(4) + matrix.get(7)*M2.get(5) + matrix.get(11)*M2.get(6) + matrix.get(15)*M2.get(7);
		this.Mtmp[8] = matrix.get(0)*M2.get(8) + matrix.get(4)*M2.get(9) + matrix.get(8)*M2.get(10) + matrix.get(12)*M2.get(11);
		this.Mtmp[9] = matrix.get(1)*M2.get(8) + matrix.get(5)*M2.get(9) + matrix.get(9)*M2.get(10) + matrix.get(13)*M2.get(11);
		this.Mtmp[10] = matrix.get(2)*M2.get(8) + matrix.get(6)*M2.get(9) + matrix.get(10)*M2.get(10) + matrix.get(14)*M2.get(11);
		this.Mtmp[11] = matrix.get(3)*M2.get(8) + matrix.get(7)*M2.get(9) + matrix.get(11)*M2.get(10) + matrix.get(15)*M2.get(11);
		this.Mtmp[12] = matrix.get(0)*M2.get(12) + matrix.get(4)*M2.get(13) + matrix.get(8)*M2.get(14) + matrix.get(12)*M2.get(15);
		this.Mtmp[13] = matrix.get(1)*M2.get(12) + matrix.get(5)*M2.get(13) + matrix.get(9)*M2.get(14) + matrix.get(13)*M2.get(15);
		this.Mtmp[14] = matrix.get(2)*M2.get(12) + matrix.get(6)*M2.get(13) + matrix.get(10)*M2.get(14) + matrix.get(14)*M2.get(15);
		this.Mtmp[15] = matrix.get(3)*M2.get(12) + matrix.get(7)*M2.get(13) + matrix.get(11)*M2.get(14) + matrix.get(15)*M2.get(15);

		matrix.put(0, this.Mtmp[0]);
		matrix.put(1, this.Mtmp[1]);
		matrix.put(2, this.Mtmp[2]);
		matrix.put(3, this.Mtmp[3]);
		matrix.put(4, this.Mtmp[4]);
		matrix.put(5, this.Mtmp[5]);
		matrix.put(6, this.Mtmp[6]);
		matrix.put(7, this.Mtmp[7]);
		matrix.put(8, this.Mtmp[8]);
		matrix.put(9, this.Mtmp[9]);
		matrix.put(10, this.Mtmp[10]);
		matrix.put(11, this.Mtmp[11]);
		matrix.put(12, this.Mtmp[12]);
		matrix.put(13, this.Mtmp[13]);
		matrix.put(14, this.Mtmp[14]);
		matrix.put(15, this.Mtmp[15]);
	}

	public void pushMatrix()
	{
		matrixStack.push(copy());
	}
	
	public FloatBuffer copy() {
		float[] tmp = new float[16];
		tmp[0] = matrix.get(0);
		tmp[1] = matrix.get(1);
		tmp[2] = matrix.get(2);
		tmp[3] = matrix.get(3);
		tmp[4] = matrix.get(4);
		tmp[5] = matrix.get(5);
		tmp[6] = matrix.get(6);
		tmp[7] = matrix.get(7);
		tmp[8] = matrix.get(8);
		tmp[9] = matrix.get(9);
		tmp[10] = matrix.get(10);
		tmp[11] = matrix.get(11);
		tmp[12] = matrix.get(12);
		tmp[13] = matrix.get(13);
		tmp[14] = matrix.get(14);
		tmp[15] = matrix.get(15);

		FloatBuffer tmpBuff = BufferUtils.newFloatBuffer(16);
		tmpBuff.put(tmp);
		tmpBuff.rewind();
		return tmpBuff;
	}
	
	public void popMatrix()
	{
		FloatBuffer tmp = matrixStack.pop();
		matrix.put(0, tmp.get(0));
		matrix.put(1, tmp.get(1));
		matrix.put(2, tmp.get(2));
		matrix.put(3, tmp.get(3));
		matrix.put(4, tmp.get(4));
		matrix.put(5, tmp.get(5));
		matrix.put(6, tmp.get(6));
		matrix.put(7, tmp.get(7));
		matrix.put(8, tmp.get(8));
		matrix.put(9, tmp.get(9));
		matrix.put(10, tmp.get(10));
		matrix.put(11, tmp.get(11));
		matrix.put(12, tmp.get(12));
		matrix.put(13, tmp.get(13));
		matrix.put(14, tmp.get(14));
		matrix.put(15, tmp.get(15));
	}

	public void setShaderMatrix(int shaderMatrixPointer)
	{
		this.shaderMatrixPointer = shaderMatrixPointer;
		Gdx.gl.glUniformMatrix4fv(shaderMatrixPointer, 1, false, matrix);
	}

	public void setShaderMatrix()
	{
		Gdx.gl.glUniformMatrix4fv(shaderMatrixPointer, 1, false, matrix);
	}
	
	public Vector3 forward() {
		return new Vector3(matrix.get(4), matrix.get(5), matrix.get(6));
	}
	
	public void print() {
		System.out.println();
		System.out.println(matrix.get(0) + " " + matrix.get(4) + " " + matrix.get(8) + " " + matrix.get(12));
		System.out.println(matrix.get(1) + " " + matrix.get(5) + " " + matrix.get(9) + " " + matrix.get(13));
		System.out.println(matrix.get(2) + " " + matrix.get(6) + " " + matrix.get(10) + " " + matrix.get(14));
		System.out.println(matrix.get(3) + " " + matrix.get(7) + " " + matrix.get(11) + " " + matrix.get(15));
		System.out.println();
	}
}
