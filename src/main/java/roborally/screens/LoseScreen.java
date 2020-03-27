package roborally.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import roborally.Application;

// Complementary documentation: https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/Screen.html

public class LoseScreen implements Screen {
    private final Application app;
    private final Stage stage;
    private Skin skin;

    public LoseScreen(final Application app){
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
        buttons();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(25f, 25f, 25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(v);

        stage.draw();

        app.batch.begin();
        app.font.draw(app.batch, "Game Over",Application.WIDTH/2f,Application.HEIGHT/2f);
        app.batch.end();
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

    private void queueAssets(){
        this.skin = new Skin();
        this.skin.addRegions(app.assets.get("ui/uiskin.atlas", TextureAtlas.class));
        this.skin.add("default-font",app.font);
        this.skin.load(Gdx.files.internal("ui/uiskin.json"));
    }

    private void buttons() {
        TextButton exitButton = new TextButton("Exit", skin, "default");
        exitButton.setPosition(850, 300);
        exitButton.setSize(300, 100);
        exitButton.getLabel().setFontScale(3.0f);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stage.addActor(exitButton);
    }
}

