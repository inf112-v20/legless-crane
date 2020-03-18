package roborally;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import roborally.gamelogic.GameLogic;
import roborally.screens.GameScreen;
import roborally.screens.MenuScreen;
import roborally.screens.LoadingScreen;

public class Application extends Game {
    // Hardcoded board size is a 7:9 ratio
    public static final int WIDTH = 2000;
    public static final int HEIGHT = 1500;

    public SpriteBatch batch;
    public BitmapFont font;
    public OrthographicCamera camera;
    public AssetManager assets;
    public LoadingScreen loadingScreen;
    public MenuScreen menuScreen;
    public GameScreen gameScreen;

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "RoboRally";
        config.width = WIDTH;
        config.height = HEIGHT;
        new LwjglApplication(new Application(), config);
    }

    @Override
    public void create() {
        // "Start-state"
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);
        assets = new AssetManager();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLACK);

        loadingScreen = new LoadingScreen(this);
        menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this);

        // Start-screen:
        this.setScreen(new LoadingScreen(this));
    }

    @Override
    public void resize(int width, int height) {/*intentionally empty method*/}

    @Override
    public void pause() {/*intentionally empty method*/}

    @Override
    public void resume() {/*intentionally empty method*/}

    @Override
    public void dispose(){
        batch.dispose();
        font.dispose();
        assets.dispose();
        loadingScreen.dispose();
        menuScreen.dispose();
        gameScreen.dispose();
    }
}
