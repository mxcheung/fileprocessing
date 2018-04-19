package au.com.maxcheung.futureclearer.flatfile;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import au.com.maxcheung.futureclearer.App;
import au.com.maxcheung.futureclearer.csv.CsvReader;
import au.com.maxcheung.futureclearer.csv.CsvWriter;
import au.com.maxcheung.futureclearer.model.FlatFileSpec;
import au.com.maxcheung.futureclearer.model.FutureTransaction;
import au.com.maxcheung.futureclearer.model.FutureTransactionDTO;
import au.com.maxcheung.futureclearer.model.FutureTransactionSummary;
import au.com.maxcheung.futureclearer.transform.FutureTransactionSummaryTransformer;
import au.com.maxcheung.futureclearer.transform.FutureTransactionTransformer;

/**
 * Unit tests for {@link App}.
 * 
 * @author Max Cheung <max.cheung@optusnet.com.au>
 *
 */
public class FutureClearingTest {

    protected static final String FILESPEC_FILEPATH = "src\\test\\resources\\filespec\\";
    private static final String FUTURE_FILESPEC_CSV = "future-filespec.csv";
    private static final String DATAFILE_TXT = "datafile.txt";

    private FlatFileReader reader;
    private FutureTransactionTransformer futureTransactionTransformer;
    private FutureTransactionSummaryTransformer futureTransactionSummaryTransformer;
    private CsvWriter writer;

    @Before
    public void setup() {
        reader = new FlatFileReader(new CsvReader(FlatFileSpec.class));
        futureTransactionTransformer = new FutureTransactionTransformer();
        futureTransactionSummaryTransformer = new FutureTransactionSummaryTransformer();
        writer = new CsvWriter(FutureTransactionSummary.class);

    }

    @Test
    public void shouldReadFixedLengthFile() throws Exception {
        String specFile = FILESPEC_FILEPATH + FUTURE_FILESPEC_CSV;
        String dataFile = FILESPEC_FILEPATH + DATAFILE_TXT;
        List<FutureTransactionDTO> transactions = reader.read(specFile, dataFile);
        
        assertEquals(717, transactions.size());
        List<FutureTransaction> futuretransactions = new ArrayList<FutureTransaction>(); 
        for (FutureTransactionDTO transaction : transactions) {
            futuretransactions.add(futureTransactionTransformer.transform(transaction));
        }
        
        FutureTransactionSummary summaryRow = new FutureTransactionSummary();
        summaryRow.setClientInfo("Client_Information");
        summaryRow.setProductInfo("Product_Information");
        summaryRow.setTransactionInfo("Transaction_Information");
        
        List<FutureTransactionSummary> summaryRows = new ArrayList<FutureTransactionSummary>(); 
        summaryRows.add(summaryRow);
        List<FutureTransactionSummary> summary = futureTransactionSummaryTransformer.transform(futuretransactions);
        summaryRows.addAll(summary);
        assertEquals(5, summary.size());
        String rfiMasterFilePath = FILESPEC_FILEPATH + "out.csv";
        writer.write(summaryRows, rfiMasterFilePath);

    }

}
