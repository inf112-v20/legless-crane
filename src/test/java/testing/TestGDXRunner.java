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
                //empty to run headless
            }

            @Override
            public void resize(int i, int i1) {
                //empty to run headless
            }

            @Override
            public void render() {
                //empty to run headless
            }

            @Override
            public void pause() {
                //empty to run headless
            }

            @Override
            public void resume() {
                //empty to run headless
            }

            @Override
            public void dispose() {
                //empty to run headless
            }
        });
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl = Gdx.gl20;
    }
}
