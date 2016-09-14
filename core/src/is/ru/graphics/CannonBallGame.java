package is.ru.graphics;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import java.awt.Canvas;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.utils.BufferUtils;

import is.ru.graphics.gameobjects.Camera;
import is.ru.graphics.gameobjects.Cannon;
import is.ru.graphics.gameobjects.CannonBall;
import is.ru.graphics.gameobjects.GameCanvas;
import is.ru.graphics.gameobjects.GameObject;
import is.ru.graphics.gameobjects.RectangleObstacle;
import is.ru.graphics.gameobjects.Target;
import is.ru.graphics.graphics.RectangleGraphics;
import is.ru.graphics.math.ModelMatrix;

public class CannonBallGame extends ApplicationAdapter {

	private FloatBuffer modelMatrix;
	private FloatBuffer projectionMatrix;

	private int renderingProgramID;
	private int vertexShaderID;
	private int fragmentShaderID;

	private int positionLoc;

	private int modelMatrixLoc;
	private int projectionMatrixLoc;

	private int colorLoc;
	
	private static CannonBallGame instance = new CannonBallGame();
	
	private ArrayList<GameObject> gameObjects;
	private ArrayList<GameObject> addedGameObjects;
	private ArrayList<GameObject> removedGameObjects;
	
	private CannonBallGame() {
		gameObjects = new ArrayList<GameObject>();
		gameObjects.add(new Cannon());
		gameObjects.add(new Target());
		//gameObjects.add(new RectangleObstacle(1, 1, 5, 5));
		gameObjects.add(new GameCanvas(new RectangleObstacle(), new RectangleObstacle()));
		
		addedGameObjects = new ArrayList<GameObject>();
		removedGameObjects = new ArrayList<GameObject>();
	}
	
	public static CannonBallGame getInstance() {
		return instance;
	}

	@Override
	public void create () {

		String vertexShaderString;
		String fragmentShaderString;

		vertexShaderString = Gdx.files.internal("shaders/simple2D.vert").readString();
		fragmentShaderString =  Gdx.files.internal("shaders/simple2D.frag").readString();

		vertexShaderID = Gdx.gl.glCreateShader(GL20.GL_VERTEX_SHADER);
		fragmentShaderID = Gdx.gl.glCreateShader(GL20.GL_FRAGMENT_SHADER);
	
		Gdx.gl.glShaderSource(vertexShaderID, vertexShaderString);
		Gdx.gl.glShaderSource(fragmentShaderID, fragmentShaderString);
	
		Gdx.gl.glCompileShader(vertexShaderID);
		Gdx.gl.glCompileShader(fragmentShaderID);

		renderingProgramID = Gdx.gl.glCreateProgram();
	
		Gdx.gl.glAttachShader(renderingProgramID, vertexShaderID);
		Gdx.gl.glAttachShader(renderingProgramID, fragmentShaderID);
	
		Gdx.gl.glLinkProgram(renderingProgramID);

		positionLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_position");
		Gdx.gl.glEnableVertexAttribArray(positionLoc);

		modelMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_modelMatrix");
		projectionMatrixLoc	= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_projectionMatrix");

		colorLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_color");

		Gdx.gl.glUseProgram(renderingProgramID);


		float[] mm = new float[16];

		mm[0] = 1.0f; mm[4] = 0.0f; mm[8] = 0.0f; mm[12] = 0.0f;
		mm[1] = 0.0f; mm[5] = 1.0f; mm[9] = 0.0f; mm[13] = 0.0f;
		mm[2] = 0.0f; mm[6] = 0.0f; mm[10] = 1.0f; mm[14] = 0.0f;
		mm[3] = 0.0f; mm[7] = 0.0f; mm[11] = 0.0f; mm[15] = 1.0f;

		modelMatrix = BufferUtils.newFloatBuffer(16);
		modelMatrix.put(mm);
		modelMatrix.rewind();

		Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, modelMatrix);

		//COLOR IS SET HERE
		Gdx.gl.glUniform4f(colorLoc, 0.7f, 0.2f, 0, 1);
		
		ModelMatrix.main.setShaderMatrix(modelMatrixLoc);
		
		// Assign shader to RectangleGraphics
		RectangleGraphics.create(positionLoc);
		
		Camera.OrthographicProjection2D(-15, 15, -3, 27);
		Camera.setProjectionMatrix(projectionMatrixLoc);
	}

	@Override
	public void render () {
		update();
		display();
	}
	
	public void addGameObject(GameObject object) {
		addedGameObjects.add(object);
	}
	
	public void removeGameObject(GameObject object) {
		removedGameObjects.remove(object);
	}
	
	/**
	 * Updates all objects in the game world
	 */
	private void update() {
		float deltatime = Gdx.graphics.getDeltaTime();
		
		for(GameObject o : gameObjects) {
			o.update(deltatime);
		}
		
		gameObjects.addAll(addedGameObjects);
		gameObjects.removeAll(removedGameObjects);
		addedGameObjects.clear();
		removedGameObjects.clear();
	}
	
	/**
	 * Displays all objects in the game world
	 */
	private void display() {
		clearScreen();
		
		for(GameObject o : gameObjects) {
			o.draw(colorLoc);
		}
	}
	
	private void clearScreen() {
		Gdx.gl.glClearColor(0.3f, 0.6f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	private void OrthographicProjection2D(float left, float right, float bottom, float top) {
		float[] pm = new float[16];

		pm[0] = 2.0f / (right - left); pm[4] = 0.0f; pm[8] = 0.0f; pm[12] = -(right + left)/(right - left);
		pm[1] = 0.0f; pm[5] = 2.0f / (top - bottom); pm[9] = 0.0f; pm[13] = -(top + bottom)/(top - bottom);
		pm[2] = 0.0f; pm[6] = 0.0f; pm[10] = 1.0f; pm[14] = 0.0f;
		pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = 0.0f; pm[15] = 1.0f;

		projectionMatrix = BufferUtils.newFloatBuffer(16);
		projectionMatrix.put(pm);
		projectionMatrix.rewind();
		Gdx.gl.glUniformMatrix4fv(projectionMatrixLoc, 1, false, projectionMatrix);
	}
	
	private void clearModelMatrix()
	{
		// m[0] m[4] m[8]  m[12]
		// m[1] m[5] m[9]  m[13]
		// m[2] m[6] m[10] m[14]
		// m[3] m[7] m[11] m[15]
		
		modelMatrix.put(0, 1.0f);
		modelMatrix.put(1, 0.0f);
		modelMatrix.put(2, 0.0f);
		modelMatrix.put(3, 0.0f);
		modelMatrix.put(4, 0.0f);
		modelMatrix.put(5, 1.0f);
		modelMatrix.put(6, 0.0f);
		modelMatrix.put(7, 0.0f);
		modelMatrix.put(8, 0.0f);
		modelMatrix.put(9, 0.0f);
		modelMatrix.put(10, 1.0f);
		modelMatrix.put(11, 0.0f);
		modelMatrix.put(12, 0.0f);
		modelMatrix.put(13, 0.0f);
		modelMatrix.put(14, 0.0f);
		modelMatrix.put(15, 1.0f);

		Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, modelMatrix);
	}
	private void setModelMatrixTranslation(float xTranslate, float yTranslate)
	{
		modelMatrix.put(12, xTranslate);
		modelMatrix.put(13, yTranslate);

		Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, modelMatrix);
	}
	private void setModelMatrixScale(float xScale, float yScale)
	{
		modelMatrix.put(0, xScale);
		modelMatrix.put(5, yScale);

		Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, modelMatrix);
	}
}