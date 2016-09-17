package is.ru.graphics;

public class GameManager {
    private static GameManager instance = new GameManager();

    private GameManager(){

    }

    public GameManager getInstance(){
        return instance;
    }
}
