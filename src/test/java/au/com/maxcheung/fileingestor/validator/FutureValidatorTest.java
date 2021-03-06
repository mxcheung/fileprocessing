package au.com.maxcheung.fileingestor.validator;

import static au.com.maxcheung.fileingestor.utils.LogUtils.futureLogMsg;
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
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import au.com.maxcheung.fileingestor.future.model.FutureTransaction;

@RunWith(MockitoJUnitRunner.class)
public class FutureValidatorTest {

    @Mock
    private Logger logger;

    private FutureValidatorImpl futureValidator;

    @Before
    public void setUp() {
        futureValidator = spy(new FutureValidatorImpl());
        when(futureValidator.logger()).thenReturn(logger);
    }

    @Test
    public void shouldLogValidationErrors() throws Exception {
        List<FutureTransaction> transactions = new ArrayList<FutureTransaction>();
        FutureTransaction transaction = new FutureTransaction();
        transaction.setRecordCode("316");
        transaction.setClientNumber("12345");
        transactions.add(transaction);
        futureValidator.validate(transactions);
        verify(logger, times(1)).error(futureLogMsg("line: 1 : recordCode : 316 : must match \"[3][1][5]\""));
        verify(logger, times(1))
                .error(futureLogMsg("line: 1 : clientNumber : 12345 : numeric value out of bounds (<4 digits>.<0 digits> expected)"));
        verify(logger, times(1)).info(anyString(), anyInt());
        verifyNoMoreInteractions(logger);
    }

}
