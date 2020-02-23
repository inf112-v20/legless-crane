package roborally.screens;

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
import roborally.gui.Renderer;

public class MenuScreen implements Screen {
    private final Renderer app;
    private Stage stage;
    private Skin skin;
    private Image logo;

    private TextButton buttonPlay;
    private TextButton buttonQuit;

    public MenuScreen(final Renderer app){
        this.app = app;
        this.stage = new Stage(new StretchViewport(Renderer.WIDTH, Renderer.HEIGHT, app.camera));
    }

    private void update(float f){
        stage.act(f);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        this.skin = new Skin();
        this.skin.addRegions(app.assets.get("ui/uiskin.atlas", TextureAtlas.class));
        this.skin.add("default-font",app.font);
        this.skin.load(Gdx.files.internal("ui/uiskin.json"));

        Texture splashTex = new Texture(Gdx.files.internal("img/logo.png"));
        logo = new Image(splashTex);
        logo.setPosition(stage.getWidth()/2-300,stage.getHeight()/2-16);
        stage.addActor(logo);

        initButtons();
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
    }

    private void queueAssets(){
        app.assets.load("ui/uiskin.atlas", TextureAtlas.class);
    }
    private void initButtons(){

        buttonPlay = new TextButton("Let's play",skin, "default");
        buttonPlay.setPosition(850,300);
        buttonPlay.setSize(300,100);
        buttonPlay.getLabel().setFontScale(3.0f);
        //buttonPlay.addAction(sequence(alpha(0),parallel(fadeIn(-5f), moveBy(0,-20,.5f, Interpolation.pow5Out))));
        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                app.setScreen(app.gameScreen);
            }
        });

        buttonQuit = new TextButton("Quit",skin, "default");
        buttonQuit.setPosition(850,150);
        buttonQuit.setSize(300,100);
        buttonQuit.getLabel().setFontScale(3.0f);
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
