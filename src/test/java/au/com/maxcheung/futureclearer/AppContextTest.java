package au.com.maxcheung.futureclearer;

import org.junit.Before;
import org.junit.Test;


public class AppContextTest {

    private static final String SERVER_PORT_ARG = "server.port=0";
    
    @Before
    public void setUp() {
     //   System.setSecurityManager(new ExitDeniedSecurityManager());
    }

    @Test
    public void shouldLoadContext() {
     //   assertEquals(1, App.makeMap(new String[] { SERVER_PORT_ARG }).size());
      //  assertEquals(0, App.makeMap(new String[] { "some random argument" }).size());
    }

    @Test
    public void shouldRunAppWithServerPort() throws Exception {
        String[] properties = new String[1];
        properties[0] = SERVER_PORT_ARG;
        App.main(properties);
       /* 
        try {
            Assert.fail("Expected exit");
        } catch (ExitSecurityException e) {
            int status = e.getStatus();
            Assert.assertEquals(0, status);
        }
        */
    }
}


