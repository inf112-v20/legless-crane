package roborally.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import roborally.Application;

/**
 * Refers to GameScreen for complementary documentation.
 */
public class LoadingScreen implements Screen {
    // Constructors in the "screen-classses" are equal to Create() in the Renderer-class
    private final Application app;
    private final ShapeRenderer shapeRenderer;
    private float progress;

    public LoadingScreen(final Application app){
        this.app = app;
        this.shapeRenderer = new ShapeRenderer();

        queueAssets();
    }

    private void update(){
        progress = MathUtils.lerp(progress, app.assets.getProgress(),.2f);

        if (app.assets.update() && progress <= app.assets.getProgress()-.001f)       // Directs to game after all upload. (What is inside ququeAssets();)
            app.setScreen(app.menuScreen);
    }

    @Override
    public void show() {
        System.out.println("Loading");
        this.progress = 0f;
        queueAssets();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(25f, 25f, 25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(32,app.camera.viewportHeight/2-8,app.camera.viewportWidth-64, 16);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(32,app.camera.viewportHeight/2-8, progress* (app.camera.viewportWidth -64), 16);
        shapeRenderer.end();

        app.batch.begin();
        app.font.draw(app.batch, "Screen is loading",650,650);
        app.batch.end();

    }

    @Override
    public void resize(int i, int i1) {/*intentionally empty method*/}

    @Override
    public void pause() {/*intentionally empty method*/}

    @Override
    public void resume() {/*intentionally empty method*/}

    @Override
    public void hide() {/*intentionally empty method*/}

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    private void queueAssets(){
            app.assets.load("ui/uiskin.atlas", TextureAtlas.class);
    }
}
