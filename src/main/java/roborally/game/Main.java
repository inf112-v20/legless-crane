package roborally.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import roborally.gui.Renderer;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RoboRally";
        // Hardcoded board size is a 7:9 ratio
        cfg.width = 2000;
        cfg.height = 1500;
        new LwjglApplication(new Renderer(), cfg);
    }
}
