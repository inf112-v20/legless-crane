package RoboRally.Game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import RoboRally.Renderer.Renderer;

public class Main {

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RoboRally";
            
        
        
        // Hardcoded board size is a 7:9 ratio
        cfg.width = 840;
        cfg.height = 1080;

        new LwjglApplication(new Renderer(), cfg);
    }
}
