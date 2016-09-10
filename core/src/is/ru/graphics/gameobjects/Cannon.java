package is.ru.graphics.gameobjects;

import is.ru.graphics.graphics.RectangleGraphics;

public class Cannon extends GameObject {
	
	private float width = 100f;
	private float height = 200f;
	
	@Override
	public void update(float deltatime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		RectangleGraphics.drawSolidSquare();
	}

}
