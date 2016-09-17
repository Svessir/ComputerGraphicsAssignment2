package is.ru.graphics;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

import is.ru.graphics.gameobjects.Camera;
import is.ru.graphics.gameobjects.GameObject;
import is.ru.graphics.gameobjects.LineObstacle;
import is.ru.graphics.gameobjects.Target;
import is.ru.graphics.math.ModelMatrix;

public class LevelManager {

	private static LevelManager instance = new LevelManager();
	
	private LevelManager() {
	}
	
	protected static LevelManager getInstance() {
		return instance;
	}
	
	public LevelInfo getNextLevel() {
		Target target = new Target();
		Vector3 coord = getRandomTargetCoord();
		ModelMatrix transform = new ModelMatrix();
		transform.addTranslation(coord.x, coord.y, coord.z);
		target.setTransform(transform);
		ArrayList<GameObject> list = getTargetObstacles(coord);
		list.add(target);
		return new LevelInfo(list);
	}
	
	private ArrayList<GameObject> getTargetObstacles(Vector3 targetPosition) {
		Vector3 point1 = new Vector3(targetPosition.x - 25.0f, targetPosition.y + 25.0f, 1);
		Vector3 point2 = new Vector3(targetPosition.x - 25.0f, targetPosition.y - 25.0f, 1);
		Vector3 point3 = new Vector3(targetPosition.x + 25.0f, targetPosition.y - 25.0f, 1);
		
		ArrayList<GameObject> list = new ArrayList<GameObject>();
		list.add(new LineObstacle(point1, point2));
		list.add(new LineObstacle(point2, point3));
		return list;
	}
	
	private Vector3 getRandomTargetCoord() {
		float x = (float)Math.random() * Gdx.graphics.getWidth();
		float y = (float)Math.random() * Gdx.graphics.getHeight();
		return Camera.screenToWorld(new Vector3(x,y,1));
	}
}
