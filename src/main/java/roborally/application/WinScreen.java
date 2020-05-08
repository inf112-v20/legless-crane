package roborally.application;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;

// Complementary documentation: https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/Screen.html

public class WinScreen implements Screen {
    private final Application app;
    private final Stage stage;

    public WinScreen(final Application app){
        this.app = app;
        this.stage = new Stage(new StretchViewport(Application.WIDTH, Application.HEIGHT, app.camera));
    }

    private void update(float f){
        stage.act(f);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();
        queueAssets();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(25f, 25f, 25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(v);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {/*intentionally empty method*/}

    @Override
    public void pause() {/*intentionally empty method*/}

    @Override
    public void resume() {/*intentionally empty method*/}

    @Override
    public void hide() {/*intentionally empty method*/}

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void queueAssets() {
        Texture splashTex = new Texture(Gdx.files.internal("img/victory.png"));
        Image logo = new Image(splashTex);
        logo.setPosition(stage.getWidth() / 9 + 50, stage.getHeight() / 3);
        stage.addActor(logo);
    }
}

