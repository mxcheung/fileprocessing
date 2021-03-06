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
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ConfigurableApplicationContext;

import au.com.maxcheung.fileingestor.App;
import au.com.maxcheung.fileingestor.future.service.FutureService;

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

    @Test
    public void shouldAcceptParameter() throws Exception {
        String[] args = {"Pepperoni", "Black Olives"};
        app.run(args);
        verify(futureService, times(1)).futureFileLoad("Pepperoni", "Black Olives");
        verifyNoMoreInteractions(futureService);
        verify(context, atLeastOnce()).close();
    }

    @Test
    public void shouldSkipLoadWhenIncorrectParameter() throws Exception {
        String[] args = {"Pepperoni"};
        app.run(args);
        verify(futureService, times(0)).futureFileLoad(anyString(), anyString());
        verifyNoMoreInteractions(futureService);
        verify(context, atLeastOnce()).close();
    }
    
    @Test
    public void shouldNoInteractionsWithInvalidArguments() throws Exception {
        String[] args = {"Pepperoni"};
        App.main(args);
        verify(futureService, times(0)).futureFileLoad(anyString(), anyString());
        verifyNoMoreInteractions(futureService);
        verifyNoMoreInteractions(context);
    }


}
