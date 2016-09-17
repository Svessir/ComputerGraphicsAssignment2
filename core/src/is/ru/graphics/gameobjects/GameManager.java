package is.ru.graphics.gameobjects;

/**
 * Created by Jóna on 17.9.2016.
 */
public class GameManager {
    private static GameManager instance = new GameManager();

    private GameManager(){

    }

    public GameManager getInstance(){
        return instance;
    }
}
