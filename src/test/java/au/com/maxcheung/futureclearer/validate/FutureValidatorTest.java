package au.com.maxcheung.futureclearer.validate;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.event.LoggingEvent;

import au.com.maxcheung.futureclearer.model.FutureTransaction;


@RunWith(MockitoJUnitRunner.class)
public class FutureValidatorTest {

    @Mock
    private Logger logger;

    @Captor
    private ArgumentCaptor<LoggingEvent> captorLoggingEvent;

    private FutureValidator futureValidator;

    @Before
    public void setup() {
        futureValidator = spy(new FutureValidator());
        when(futureValidator.logger()).thenReturn(logger);
    }

    @Test
    public void shouldLogValidationErrors() throws Exception {
        List<FutureTransaction> transactions = new ArrayList<FutureTransaction>();
        FutureTransaction transaction = new FutureTransaction();
        transaction.setClientNumber("clientNumber");
        transactions.add(transaction);
        futureValidator.validate(transactions);
        verify(logger, times(1)).error(anyString());
        verify(logger, times(1)).info(anyString(), anyInt());
        verifyNoMoreInteractions(logger);
    }

}
