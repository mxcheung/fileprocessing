package au.com.maxcheung.service.futureclearer.csv;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import au.com.maxcheung.service.futureclearer.csv.CsvReader;
import au.com.maxcheung.service.futureclearer.csv.FlatFileSpecRow;

public class FileSpecCSVReaderTest {

	private CsvReader fileSpecCSVReader;
	protected static final String FILESPEC_FILEPATH = "src\\test\\resources\\filespec\\";

	@Before
	public void setup() {
		fileSpecCSVReader = new CsvReader(FlatFileSpecRow.class);
	}

	@Test
	public void shouldReadFileSpecFromFile() throws JsonProcessingException, FileNotFoundException, IOException {
		String rfiMasterFilePath = FILESPEC_FILEPATH + "future-filespec.csv";
		List<FlatFileSpecRow> rows = fileSpecCSVReader.readCsv(rfiMasterFilePath);
		assertEquals(34, rows.size());
		assertEquals(3, rows.get(0).getFieldSize());
	}

}