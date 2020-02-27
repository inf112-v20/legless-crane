package testing;

import org.junit.jupiter.api.Test;
import roborally.Application;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class Tester  {
    Application app = mock(Application.class);

    @Test
    public void initAppNotNullTest(){
        assertNotNull(app);
    }
}

