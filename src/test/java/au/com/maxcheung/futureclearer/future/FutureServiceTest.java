package au.com.maxcheung.futureclearer.future;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import au.com.maxcheung.futureclearer.flatfile.FlatFileReader;
import au.com.maxcheung.futureclearer.model.FutureTransaction;
import au.com.maxcheung.futureclearer.model.FutureTransactionSummary;
import au.com.maxcheung.futureclearer.transform.FutureTransformer;
import au.com.maxcheung.futureclearer.validate.FutureValidator;

@RunWith(MockitoJUnitRunner.class)
public class FutureServiceTest extends FutureTransactionLoadRequestTst {

    private FutureService futureService;

    @Mock
    FlatFileReader flatFileReader;

    @Mock
    FutureValidator futureValidator;

    @Mock
    FutureTransformer futureTransformer;

    @Mock
    FutureWriter futureWriter;

    @Before
    public void setup() {
        futureService = new FutureService(flatFileReader, futureValidator, futureTransformer, futureWriter);
    }

    @Test
    public void shouldLoadLookup() throws UnexpectedInputException, ParseException, Exception {
        List<FutureTransaction> data = new ArrayList<FutureTransaction>();
        FutureTransaction transaction = new FutureTransaction();
        data.add(transaction);
        when(flatFileReader.read(DATAFILE_NAME)).thenReturn(data);
        List<FutureTransactionSummary> lookupRows = futureService.lookupLoad(DATAFILE_NAME, REPORTFILE_NAME);
        assertEquals(0, lookupRows.size());
        verify(futureValidator, times(1)).validate(any());
        verifyNoMoreInteractions(futureValidator);
        verify(futureWriter, times(1)).write(any(), anyString());
        verifyNoMoreInteractions(futureWriter);
    }

    @Test(expected = FileLoadException.class)
    public void shouldThrowFileLoadException() throws UnexpectedInputException, ParseException, Exception {
        doThrow(new FileNotFoundException()).when(flatFileReader).read(DATAFILE_NAME);
        futureService.lookupLoad(DATAFILE_NAME, REPORTFILE_NAME);
    }

}