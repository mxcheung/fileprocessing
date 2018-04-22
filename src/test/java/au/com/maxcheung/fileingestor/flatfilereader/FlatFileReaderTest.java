package au.com.maxcheung.fileingestor.flatfilereader;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import au.com.maxcheung.fileingestor.App;
import au.com.maxcheung.fileingestor.future.model.FutureTransaction;

/**
 * Unit tests for {@link App}.
 * 
 * @author Max Cheung <max.cheung@optusnet.com.au>
 *
 */
public class FlatFileReaderTest {

    protected static final String FILESPEC_FILEPATH = "src/test/resources/filespec/";
    private static final String DATAFILE_TXT = "datafile.txt";
    private static final int TXN_SIZE = 717;

    private FlatFileReader reader;

    @Before
    public void setUp() throws IOException {
        // reader = new FlatFileReader(new CsvReader(FlatFileSpec.class));
        reader = new FlatFileReaderImpl();

    }

    @Test
    public void shouldReadFixedLengthFile() throws Exception {
        String dataFile = FILESPEC_FILEPATH + DATAFILE_TXT;
        List<FutureTransaction> transactions = reader.read(dataFile);
        assertEquals(TXN_SIZE, transactions.size());
    }

}
