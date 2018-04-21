package au.com.maxcheung.futureclearer.csv;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import au.com.maxcheung.futureclearer.model.FutureTransactionSummary;

public class CSVWriterSummaryTest {

    private static final String CLIENT_INFO = "clientInfo";
    
    private CsvWriter genericCSVWriter;
    protected static final String FILESPEC_FILEPATH = "src\\test\\resources\\";

    @Before
    public void setup() {
        genericCSVWriter = new CsvWriter(FutureTransactionSummary.class);
    }

    @Test
    public void shouldWriteOutputFile() throws FileNotFoundException, IOException {
        String rfiMasterFilePath = FILESPEC_FILEPATH + "summary.csv";
        List<FutureTransactionSummary> rows = new ArrayList<FutureTransactionSummary>();
        FutureTransactionSummary summaryRow = new FutureTransactionSummary();
        summaryRow.setClientInfo(CLIENT_INFO);
        rows.add(summaryRow);
        genericCSVWriter.write(rows, rfiMasterFilePath);
    }

}