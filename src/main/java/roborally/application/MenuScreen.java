package roborally.application;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

// Complementary documentation: https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/Screen.html

public class MenuScreen implements Screen {
    private final Application app;
    private final Stage stage;
    private Skin skin;

    public MenuScreen(final Application app){
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
        app.font.draw(app.batch, "MENU",970,1900);
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

        Texture splashTex = new Texture(Gdx.files.internal("img/logo.png"));
        Image logo = new Image(splashTex);
        logo.setPosition(stage.getWidth()/2-300,stage.getHeight()/2-16);
        stage.addActor(logo);
    }
    
    private void buttons(){
        TextButton buttonPlay = new TextButton("Let's play", skin, "default");
        buttonPlay.setPosition(850,300);
        buttonPlay.setSize(300,100);
        buttonPlay.getLabel().setFontScale(3.0f);
        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){ app.setScreen(app.gameScreen); }});

        TextButton buttonQuit = new TextButton("Quit", skin, "default");
        buttonQuit.setPosition(850,150);
        buttonQuit.setSize(300,100);
        buttonQuit.getLabel().setFontScale(3.0f);
        buttonQuit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){ Gdx.app.exit(); }});

        stage.addActor(buttonPlay);
        stage.addActor(buttonQuit);
    }
}
