package roborally.gui;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import roborally.screens.GameScreen;
import roborally.screens.MenuScreen;
import roborally.screens.LoadingScreen;

public class Renderer extends Game implements ApplicationListener  {
    public static final int WIDTH = 1600;
    public static final int HEIGHT = 1600;

    public SpriteBatch batch;
    public BitmapFont font;
    public OrthographicCamera camera;
    public AssetManager assets;
    public LoadingScreen loadingScreen;
    public MenuScreen menuScreen;
    public GameScreen gameScreen;

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
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        // While having multiple screens: super.render() calls the render of the current screen
        super.render();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

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
