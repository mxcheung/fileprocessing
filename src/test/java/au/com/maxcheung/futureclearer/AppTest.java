package au.com.maxcheung.futureclearer;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import au.com.maxcheung.futureclearer.future.FutureService;

/**
 * Unit tests for {@link App}.
 * 
 * @author Max Cheung <max.cheung@optusnet.com.au>
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AppTest {

    /**
     * Test object.
     */
    @InjectMocks
    private App app;

    @Mock
    private FutureService futureService;

    @Mock
    private ConfigurableApplicationContext context;

    /**
     * {@link Spy} {@link SpringApplicationBuilder}.
     */
    @Spy
    private SpringApplicationBuilder builder = new SpringApplicationBuilder(App.class);

    @Test
    public void shouldAcceptParameter() throws Exception {
        String[] args = {"Pepperoni", "Black Olives"};
        app.run(args);
        verify(futureService, times(1)).lookupLoad("Pepperoni", "Black Olives");
        verifyNoMoreInteractions(futureService);
        verify(context, atLeastOnce()).close();
    }

    @Test
    public void shouldSkipLoadWhenIncorrectParameter() throws Exception {
        String[] args = {"Pepperoni"};
        app.run(args);
        verify(futureService, times(0)).lookupLoad(anyString(), anyString());
        verifyNoMoreInteractions(futureService);
        verify(context, atLeastOnce()).close();
    }
    
    @Test
    public void shouldNoInteractionsWithInvalidArguments() throws Exception {
        String[] args = {"Pepperoni"};
        App.main(args);
        verify(futureService, times(0)).lookupLoad(anyString(), anyString());
        verifyNoMoreInteractions(futureService);
        verifyNoMoreInteractions(context);
    }

}
