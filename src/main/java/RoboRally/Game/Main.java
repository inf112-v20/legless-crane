package RoboRally.Game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import RoboRally.Renderer.Renderer;

public class Main {

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RoboRally";
        //cfg.useGL30 = true;
        cfg.width = 720;
        cfg.height = 720;

        new LwjglApplication(new Renderer(), cfg);
    }
}
