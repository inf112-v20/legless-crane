package roborally.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import roborally.gui.Renderer;

public class MenuScreen implements Screen {
    private final Renderer app;
    private ShapeRenderer shapeRenderer;
    private Stage stage;
    private Skin skin;

    private TextButton buttonPlay;
    private TextButton buttonQuit;

    public MenuScreen(final Renderer app){
        this.app = app;
        this.stage = new Stage(new StretchViewport(Renderer.WIDTH, Renderer.HEIGHT, app.camera));
        this.shapeRenderer = new ShapeRenderer();
    }

    private void update(float f){
        stage.act(f);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear(); // reload site

        // UI: graphical representation buttons
        this.skin = new Skin();
        this.skin.addRegions(app.assets.get("ui/uiskin.atlas", TextureAtlas.class));
        this.skin.add("default-font",app.font);
        this.skin.load(Gdx.files.internal("ui/uiskin.json"));

        initButtons();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(25f, 25f, 25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(v);

        stage.draw();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        // Something here?
        shapeRenderer.end();

        app.batch.begin();
        app.font.draw(app.batch, "MENU",20,20);
        app.batch.end();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        shapeRenderer.dispose();
    }

    private void queueAssets(){
        app.assets.load("ui/uiskin.atlas", TextureAtlas.class);
    }
    private void initButtons(){

        buttonPlay = new TextButton("Let's play",skin, "default");
        buttonPlay.setPosition(650,800);
        buttonPlay.setSize(280,60);
        //buttonPlay.addAction(sequence(alpha(0),parallel(fadeIn(-5f), moveBy(0,-20,.5f, Interpolation.pow5Out))));
        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.setScreen(app.gameScreen);
            }
        });


        buttonQuit = new TextButton("Quit",skin, "default");
        buttonQuit.setPosition(650,700);
        buttonQuit.setSize(280,60);
        buttonQuit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });

        stage.addActor(buttonPlay);
        stage.addActor(buttonQuit);

    }
}
