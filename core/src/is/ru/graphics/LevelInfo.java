package is.ru.graphics;

import java.util.ArrayList;

import is.ru.graphics.gameobjects.GameObject;

public class LevelInfo {
	public ArrayList<GameObject> gameObjects;
	
	public LevelInfo(ArrayList<GameObject> list) {
		gameObjects = list;
	}
}
