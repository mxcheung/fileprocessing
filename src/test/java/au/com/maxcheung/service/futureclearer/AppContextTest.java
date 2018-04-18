package au.com.maxcheung.service.futureclearer;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class AppContextTest {

    private static final String SERVER_PORT_ARG = "server.port=0";

    @Test
    public void shouldCreatePropertiesMap() {
        assertEquals(1,App.makeMap(new String[] {SERVER_PORT_ARG}).size());
        assertEquals(0,App.makeMap(new String[] {"some random argument"}).size());
    }

    @Test
    public void shouldRunAppWithServerPort() {
        String[] properties = new String[1];
        properties[0] = SERVER_PORT_ARG;
        App.main(properties);
    }
}