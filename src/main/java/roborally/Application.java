package roborally;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import roborally.screens.*;

/**
 * Main.
 * Complementary documentation: https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/Game.html
 */

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
    public WinScreen winScreen;
    public LoseScreen loseScreen;

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "RoboRally";
        config.width = WIDTH;
        config.height = HEIGHT;
        new LwjglApplication(new Application(), config);
    }

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);
        assets = new AssetManager();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLACK);

        loadingScreen = new LoadingScreen(this);
        menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this);
        winScreen = new WinScreen(this);
        loseScreen = new LoseScreen(this);

        this.setScreen(new LoadingScreen(this));            // First screen that is being set.
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
        winScreen.dispose();
        loseScreen.dispose();
    }
}
