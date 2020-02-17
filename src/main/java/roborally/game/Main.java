package roborally.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.xml.sax.SAXException;
import roborally.gui.Renderer;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "roborally";
        // Hardcoded board size is a 7:9 ratio
        cfg.width = 840;
        cfg.height = 1080;
        new LwjglApplication(new Renderer(), cfg);
    }
}
