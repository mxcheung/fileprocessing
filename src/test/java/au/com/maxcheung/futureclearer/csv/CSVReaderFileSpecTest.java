package au.com.maxcheung.futureclearer.csv;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import au.com.maxcheung.futureclearer.model.FlatFileSpec;

public class CSVReaderFileSpecTest {

    protected static final String FILESPEC_FILEPATH = "src/test/resources/filespec/";

    private static final int THIRITYFOUR = 34;

    private static final int THREE = 3;


    private CsvReader fileSpecCSVReader;

    @Before
    public void setUp() {
        fileSpecCSVReader = new CsvReader(FlatFileSpec.class);
    }

    @Test
    public void shouldReadFileSpecFromFile() throws JsonProcessingException, FileNotFoundException, IOException {
        String rfiMasterFilePath = FILESPEC_FILEPATH + "future-filespec.csv";
        List<FlatFileSpec> rows = fileSpecCSVReader.readCsv(rfiMasterFilePath);
        assertEquals(THIRITYFOUR, rows.size());
        assertEquals(THREE, rows.get(0).getFieldSize());
    }

}