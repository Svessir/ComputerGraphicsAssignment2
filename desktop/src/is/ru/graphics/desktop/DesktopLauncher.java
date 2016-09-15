package is.ru.graphics.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import is.ru.graphics.CannonBallGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Cannon Ball!";
		config.width = 1280;
		config.height = 960;
		
		new LwjglApplication(CannonBallGame.getInstance(), config);
	}
}
