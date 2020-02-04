package RoboRally.Renderer;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;


public class Renderer extends Game {
    @Override
    public void create() {
        setScreen(new PlayScreen());
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
