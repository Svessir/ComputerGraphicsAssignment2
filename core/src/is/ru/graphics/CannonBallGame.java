package is.ru.graphics;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import com.badlogic.gdx.utils.BufferUtils;

import is.ru.graphics.gameobjects.Boundary;
import is.ru.graphics.gameobjects.Camera;
import is.ru.graphics.gameobjects.Cannon;
import is.ru.graphics.gameobjects.GameCanvas;
import is.ru.graphics.gameobjects.GameObject;
import is.ru.graphics.gameobjects.LineObstacle;
import is.ru.graphics.gameobjects.RectangleObstacle;
import is.ru.graphics.graphics.CircleGraphics;
import is.ru.graphics.graphics.LineGraphics;
import is.ru.graphics.graphics.RectangleGraphics;
import is.ru.graphics.graphics.TriangleGraphics;
import is.ru.graphics.math.Collision;
import is.ru.graphics.math.CollisionEdge;
import is.ru.graphics.math.CollisionVertex;
import is.ru.graphics.math.ModelMatrix;

public class CannonBallGame extends ApplicationAdapter {

	private FloatBuffer modelMatrix;

	private int renderingProgramID;
	private int vertexShaderID;
	private int fragmentShaderID;

	private int positionLoc;

	private int modelMatrixLoc;
	private int projectionMatrixLoc;

	private int colorLoc;
	
	// Singleton instance of the game
	private static CannonBallGame instance = new CannonBallGame();
	
	// Objects tracked within the world that need to be updated and drawn in each frame.
	private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	
	// Objects to be added after a frame. Needs to be cleared after each update cycle
	private ArrayList<GameObject> addedGameObjects = new ArrayList<GameObject>();
	
	// Objects to be removed after a frame. Needs to be cleared after each update cycle
	private ArrayList<GameObject> removedGameObjects = new ArrayList<GameObject>();
	
	private LevelInfo currentLevel;
	
	private Cannon cannon;
	
	private boolean isGameOver = false;
	
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
		
		// Assign shader to the graphics
		CircleGraphics.create(positionLoc);
		RectangleGraphics.create(positionLoc);
		LineGraphics.create(positionLoc);
        TriangleGraphics.create(positionLoc);
		
		// initialize the camera
		Camera.OrthographicProjection2D(0, Gdx.graphics.getWidth(), 0, Gdx.graphics.getHeight());
		Camera.setProjectionMatrix(projectionMatrixLoc);
		
		cannon = new Cannon();
		
		currentLevel = LevelManager.getInstance().getNextLevel();
		loadLevel(currentLevel);
	}

	@Override
	public void render () {
		input();
		update();
		display();
	}
	
	public void addGameObject(GameObject object) {
		addedGameObjects.add(object);
	}
	
	public void removeGameObject(GameObject object) {
		removedGameObjects.add(object);
	}
	
	public void endLevel() {
		isGameOver = true;
	}
	
	public Collision getCollision(GameObject obj, float deltatime) {
		CollisionVertex vertex = obj.getCollisionVertex();
		Collision latestCollision = null;
		GameObject latestCollider = null;
		
		if(vertex == null)
			return null;
		
		for(GameObject o : gameObjects) {
			Collision collision = null;
			ArrayList<CollisionEdge> edges = o.getCollisionEdges();
			
			if(!edges.isEmpty()) {
				collision = getLatestCollision(vertex.vertex, vertex.velocity, deltatime, edges);
				if(collision != null && collision.compareTo(latestCollision) > 0) {
					latestCollision = collision;
					latestCollider = o;
				}
			}
		}
		
		if(latestCollider != null)
			latestCollider.onTouch(obj);
		
		return latestCollision;
	}
	
	private Collision getLatestCollision(Vector3 vertex, Vector3 velocity, float deltatime, ArrayList<CollisionEdge> edges) {
		Collision latesCollision = null;
		
		for(CollisionEdge e : edges) {
			Collision collision = e.getCollision(vertex, velocity, deltatime);
			latesCollision = collision != null && collision.compareTo(latesCollision) > 0 ? 
					collision : latesCollision;
		}
		
		return latesCollision;
	}
	
	private void input() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.R)) {
			loadLevel(currentLevel);
		}
	}
	
	/**
	 * Updates all objects in the game world
	 */
	private void update() {
		
		if(isGameOver) {
			isGameOver = false;
			currentLevel = LevelManager.getInstance().getNextLevel();
			loadLevel(currentLevel);
			return;
		}
		
		float deltatime = Gdx.graphics.getDeltaTime();
		
		for(GameObject o : gameObjects) {
			Collision c = getCollision(o, deltatime);
			if(c != null) {
				deltatime -= c.timeToCollision;
				o.update(c.timeToCollision);
				o.onCollision(c.colliderNormal);
			}
			
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
		Gdx.gl.glClearColor(0.4f, 0f, 1f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	private void loadLevel(LevelInfo level) {
		// Add mandatory objects to the game
		gameObjects.clear();
		gameObjects.add(cannon); 														// Player cannon
		gameObjects.add(new GameCanvas(new RectangleObstacle(), new LineObstacle())); 	// To draw obstacles
		gameObjects.addAll(level.gameObjects);
		Vector3 point1 = Camera.screenToWorld(new Vector3(0, 0, 0));
		Vector3 point2 = Camera.screenToWorld(new Vector3(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0));
		gameObjects.add(new Boundary(point1.x, point2.x, point1.y, point2.y));
	}
}