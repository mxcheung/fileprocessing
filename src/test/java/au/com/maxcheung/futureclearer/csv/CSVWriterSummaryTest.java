package au.com.maxcheung.futureclearer.csv;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import au.com.maxcheung.futureclearer.model.FutureTransaction;

public class CSVWriterSummaryTest {

    private static final String ACCOUNT_NUMBER = "1";
    
    private CsvWriter genericCSVWriter;
    protected static final String FILESPEC_FILEPATH = "src\\test\\resources\\filespec\\";

    @Before
    public void setup() {
        genericCSVWriter = new CsvWriter(FutureTransaction.class);
    }

    @Test
    public void shouldWriteOutputFile() throws FileNotFoundException, IOException {
        String rfiMasterFilePath = FILESPEC_FILEPATH + "out.csv";
        List<FutureTransaction> rows = new ArrayList<FutureTransaction>();
        FutureTransaction summaryRow = new FutureTransaction();
        summaryRow.setAccountNumber(ACCOUNT_NUMBER);
        rows.add(summaryRow);
        genericCSVWriter.write(rows, rfiMasterFilePath);

    }

}