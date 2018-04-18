package au.com.maxcheung.service.futureclearer.csv;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CSVWriterTest {

    private CsvWriter genericCSVWriter;
	protected static final String FILESPEC_FILEPATH = "src\\test\\resources\\filespec\\";

    @Before
    public void setup() {
        genericCSVWriter = new CsvWriter(SummaryRow.class);
    }

    @Test
    public void shouldWriteOutputFile() throws FileNotFoundException, IOException {
        String rfiMasterFilePath = FILESPEC_FILEPATH + "out.csv";
        List<SummaryRow> rows = new ArrayList<SummaryRow>();
        SummaryRow summaryRow = new SummaryRow();
        summaryRow.setClientInfo("Client_Information");
        summaryRow.setProductInfo("Product_Information");
        summaryRow.setTotalTransactionAmount("Total_Transaction_Amount");
        rows.add(summaryRow);
        genericCSVWriter.write(rows, rfiMasterFilePath);

    }

}