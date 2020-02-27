package testing;

import org.junit.Test;
import roborally.Application;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class Tester  {
    Application app = mock(Application.class);

    @Test
    public void initAppNotNullTest(){
        assertNotNull(app);
    }
}

