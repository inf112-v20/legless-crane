package testing;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import org.mockito.Mockito;

/**
 * Runs a headless test of an Application
 * Thus all methods here are empty
 *
 * use @ExtendWith(TestGDXRunner.class) to run tests with the headless application
 */
public class TestGDXRunner implements BeforeAllCallback {
    @Override
    public void beforeAll(ExtensionContext extensionContext) throws ExceptionInInitializerError {
        new HeadlessApplication(new ApplicationListener() {
            @Override
            public void create() {
                //Headless

            }

            @Override
            public void resize(int i, int i1) {
                //Headless
            }

            @Override
            public void render() {
                //Headless

            }

            @Override
            public void pause() {
                //Headless
            }

            @Override
            public void resume() {
                //Headless
            }

            @Override
            public void dispose() {
                //Headless
            }
        });
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl = Gdx.gl20;
    }
}
