package au.com.maxcheung.fileingestor.future.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import au.com.maxcheung.fileingestor.flatfilereader.FlatFileReader;
import au.com.maxcheung.fileingestor.future.exception.FileLoadException;
import au.com.maxcheung.fileingestor.future.model.FutureTransaction;
import au.com.maxcheung.fileingestor.future.model.FutureTransactionSummary;
import au.com.maxcheung.fileingestor.transformer.FutureTransformer;
import au.com.maxcheung.fileingestor.validator.FutureValidator;
import au.com.maxcheung.fileingestor.writer.FutureWriter;

@RunWith(MockitoJUnitRunner.class)
public class FutureServiceTest extends FutureServiceTst {

    private FutureService futureService;

    @Mock
    private FlatFileReader flatFileReader;

    @Mock
    private FutureValidator futureValidator;

    @Mock
    private FutureTransformer futureTransformer;

    @Mock
    private FutureWriter futureWriter;

    @Before
    public void setUp() {
        futureService = new FutureService(flatFileReader, futureValidator, futureTransformer, futureWriter);
    }

    @Test
    public void shouldLoadFuture() throws UnexpectedInputException, ParseException, Exception {
        List<FutureTransaction> data = new ArrayList<FutureTransaction>();
        FutureTransaction transaction = new FutureTransaction();
        data.add(transaction);
        when(flatFileReader.read(DATAFILE_NAME)).thenReturn(data);
        List<FutureTransactionSummary> lookupRows = futureService.futureFileLoad(DATAFILE_NAME, REPORTFILE_NAME);
        assertEquals(0, lookupRows.size());
        verify(futureValidator, times(1)).validate(any());
        verifyNoMoreInteractions(futureValidator);
        verify(futureWriter, times(1)).write(any(), anyString());
        verifyNoMoreInteractions(futureWriter);
    }

    @Test(expected = FileLoadException.class)
    public void shouldThrowFileLoadException() throws UnexpectedInputException, ParseException, Exception {
        doThrow(new FileNotFoundException()).when(flatFileReader).read(DATAFILE_NAME);
        futureService.futureFileLoad(DATAFILE_NAME, REPORTFILE_NAME);
    }
}


