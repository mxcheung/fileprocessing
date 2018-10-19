package au.com.maxcheung.fileingestor.writer;

import static au.com.maxcheung.fileingestor.utils.LogUtils.futureLogMsg;
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

import au.com.maxcheung.fileingestor.future.model.FutureTransactionSummary;

@RunWith(MockitoJUnitRunner.class)
public class FutureWriterImplTest {

    protected static final String FILESPEC_FILEPATH = "src\\test\\resources\\";

    @Mock
    private Logger logger;

    private FutureWriterImpl futureWriter;

    @Before
    public void setUp() {
        futureWriter = spy(new FutureWriterImpl());
        when(futureWriter.logger()).thenReturn(logger);
    }

    @Test
    public void shouldWriteSummary() throws Exception {
        String rfiMasterFilePath = FILESPEC_FILEPATH + "dummy.csv";

        List<FutureTransactionSummary> transactions = new ArrayList<FutureTransactionSummary>();
        FutureTransactionSummary transaction = new FutureTransactionSummary();
        transaction.setClientInfo("client123");
        transactions.add(transaction);
        futureWriter.write(transactions, rfiMasterFilePath);
        verify(logger, times(1)).info(futureLogMsg("Written rows : {} to file : {}."), 1, rfiMasterFilePath);
        verifyNoMoreInteractions(logger);
    }

}
