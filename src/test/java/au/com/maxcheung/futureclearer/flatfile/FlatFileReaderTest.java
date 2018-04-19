package au.com.maxcheung.futureclearer.flatfile;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import au.com.maxcheung.futureclearer.App;
import au.com.maxcheung.futureclearer.csv.CsvReader;
import au.com.maxcheung.futureclearer.model.FlatFileSpec;
import au.com.maxcheung.futureclearer.model.FutureTransaction;

/**
 * Unit tests for {@link App}.
 * 
 * @author Max Cheung <max.cheung@optusnet.com.au>
 *
 */
public class FlatFileReaderTest {

    protected static final String FILESPEC_FILEPATH = "src\\test\\resources\\filespec\\";
    private static final String FUTURE_FILESPEC_CSV = "future-filespec.csv";
    private static final String DATAFILE_TXT = "datafile.txt";

    private FlatFileReader reader;

    @Before
    public void setup() {
        reader = new FlatFileReader(new CsvReader(FlatFileSpec.class));
    }

    @Test
    public void shouldReadFixedLengthFile() throws Exception {
        String specFile = FILESPEC_FILEPATH + FUTURE_FILESPEC_CSV;
        String dataFile = FILESPEC_FILEPATH + DATAFILE_TXT;
        List<FutureTransaction> transactions = reader.read(specFile, dataFile);
        assertEquals(717, transactions.size());
    }

}
