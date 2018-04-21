package au.com.maxcheung.futureclearer.future;

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

import au.com.maxcheung.futureclearer.model.FutureTransactionSummary;


@RunWith(MockitoJUnitRunner.class)
public class FutureWriterTest {

    @Mock
    private Logger logger;

    @Captor
    private ArgumentCaptor<LoggingEvent> captorLoggingEvent;

    private FutureWriter  futureWriter ;

    @Before
    public void setup() {
        futureWriter = spy(new FutureWriter());
        when(futureWriter.logger()).thenReturn(logger);
    }

    @Test
    public void shouldWriteSummary() throws Exception {
        List<FutureTransactionSummary> transactions = new ArrayList<FutureTransactionSummary>();
        FutureTransactionSummary transaction = new FutureTransactionSummary();
        transaction.setClientInfo("clientInfo");
        transactions.add(transaction);
        futureWriter.write(transactions, "dummy");
        verify(logger, times(1)).info("Written rows : {} to file : {}.",
                1,
                "dummy");
        verifyNoMoreInteractions(logger);
    }

}
