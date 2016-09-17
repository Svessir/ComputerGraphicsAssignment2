package is.ru.graphics.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import is.ru.graphics.CannonBallGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Cannon Ball!";
		config.width = 2000;
		config.height = 2000;
		
		new LwjglApplication(CannonBallGame.getInstance(), config);
	}
}
