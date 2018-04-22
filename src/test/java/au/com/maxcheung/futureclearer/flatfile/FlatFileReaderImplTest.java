package au.com.maxcheung.futureclearer.flatfile;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import au.com.maxcheung.futureclearer.App;
import au.com.maxcheung.futureclearer.csv.CsvWriter;
import au.com.maxcheung.futureclearer.model.FutureTransaction;
import au.com.maxcheung.futureclearer.model.FutureTransactionSummary;
import au.com.maxcheung.futureclearer.transform.FutureTransformer;
import au.com.maxcheung.futureclearer.transform.FutureTransformerImpl;
import au.com.maxcheung.futureclearer.validate.FutureValidator;
import au.com.maxcheung.futureclearer.validate.FutureValidatorImpl;

/**
 * Unit tests for {@link App}.
 * 
 * @author Max Cheung <max.cheung@optusnet.com.au>
 *
 */
public class FlatFileReaderImplTest {

    protected static final String FILESPEC_FILEPATH = "src/test/resources/filespec/";

    private static final int TXNS_SIZE = 717;
    private static final int SUMMARY_COUNT = 5;
    private static final String DATAFILE_TXT = "datafile.txt";

    private FlatFileReaderImpl reader;
    private CsvWriter writer;
    private FutureTransformer futureTransactionSummaryTransformer;
    private FutureValidator futureValidator;

    @Before
    public void setUp() throws IOException {
        // reader = new FlatFileReader(new CsvReader(FlatFileSpec.class));
        reader = new FlatFileReaderImpl();
        writer = new CsvWriter(FutureTransactionSummary.class);
        futureTransactionSummaryTransformer = new FutureTransformerImpl();
        futureValidator = new FutureValidatorImpl();
    }

    @Test
    public void shouldReadFixedLengthFile() throws Exception {
        String dataFile = FILESPEC_FILEPATH + DATAFILE_TXT;
        List<FutureTransaction> transactions = reader.read(dataFile);
        assertEquals(TXNS_SIZE, transactions.size());
        futureValidator.validate(transactions);
        List<FutureTransactionSummary> summary = futureTransactionSummaryTransformer.transform(transactions);
        assertEquals(SUMMARY_COUNT, summary.size());
        String rfiMasterFilePath = FILESPEC_FILEPATH + "out.csv";
        writer.write(summary, rfiMasterFilePath);
    }

}
