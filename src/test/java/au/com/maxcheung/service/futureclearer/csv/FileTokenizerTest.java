package au.com.maxcheung.service.futureclearer.csv;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;

import au.com.maxcheung.service.futureclearer.App;

/**
 * Unit tests for {@link App}.
 * 
 * @author Max Cheung <max.cheung@optusnet.com.au>
 *
 */
public class FileTokenizerTest {

	private CsvReader fileSpecCSVReader;
	protected static final String FILESPEC_FILEPATH = "src\\test\\resources\\filespec\\";

	@Before
	public void setup() {
		fileSpecCSVReader = new CsvReader(FlatFileSpecRow.class);
	}

	/**
	 * Tokenize fixed length string
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@Test
	public void shouldTokenize() throws FileNotFoundException, IOException {
		String rfiMasterFilePath = FILESPEC_FILEPATH + "future-filespec.csv";
		List<FlatFileSpecRow> rows = fileSpecCSVReader.readCsv(rfiMasterFilePath);
		assertEquals(34, rows.size());
		FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();
		List<String> columnNames = rows.stream().map(e -> e.getColumnName()).collect(Collectors.toList());
		tokenizer.setNames(columnNames.toArray(new String[0]));
		List<Range> rangeList = rows.stream().map(e -> new Range(e.getStart(), e.getEnd()))
				.collect(Collectors.toList());
		tokenizer.setColumns(rangeList.toArray(new Range[0]));
		String line = "315CL  432100020001SGXDC FUSGX NK    20100910JPY01B 0000000001 0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012380     688032000092500000000             O";
		FieldSet fields = tokenizer.tokenize(line);
		assertEquals(34, fields.getFieldCount());
	}

	@Test
	public void shouldTokenizexxx() throws FileNotFoundException, IOException {

		String rfiMasterFilePath = FILESPEC_FILEPATH + "future-filespec.csv";
		List<FlatFileSpecRow> rows = fileSpecCSVReader.readCsv(rfiMasterFilePath);
		assertEquals(34, rows.size());

		FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();
		// tokenizer.setNames(new String[] { "recordCode", "clientType", "clientNumber",
		// "accountNumber" });

		// Range[] ranges = new Range[] { new Range(1, 3), new Range(4, 7), new Range(8,
		// 11), new Range(12,176) };
		// tokenizer.setColumns(new Range[] { new Range(1, 3), new Range(4, 7), new
		// Range(8, 11), new Range(12,176) });
		// ranges.

		List<Range> rangeList = new ArrayList<Range>();

		// List<String> columnNames = new ArrayList<String>();
		for (FlatFileSpecRow row : rows) {
			rangeList.add(new Range(row.getStart(), row.getEnd()));
			// columnNames.add(row.getColumnName());

		}
		/*
		 * rangeList.add(new Range(1, 3)); rangeList.add(new Range(4, 7));
		 * rangeList.add(new Range(8, 11)); rangeList.add(new Range(12, 176));
		 * columnNames.add("clientType"); columnNames.add("clientNumber");
		 * columnNames.add("accountNumber");
		 */
		Range[] ranges = rangeList.toArray(new Range[0]);

		List<String> columnNames = rows.stream().map(e -> e.getColumnName()).collect(Collectors.toList());
		// String[] columnNames = (String[]) rows.stream().map(e ->
		// e.getColumnName()).toArray();
		tokenizer.setNames(columnNames.toArray(new String[0]));

		List<Range> rangeList2 = rows.stream().map(e -> new Range(e.getStart(), e.getEnd()))
				.collect(Collectors.toList());
		tokenizer.setColumns(rangeList2.toArray(new Range[0]));

		String line = "315CL  432100020001SGXDC FUSGX NK    20100910JPY01B 0000000001 0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012380     688032000092500000000             O";
		int y = line.length();
		FieldSet fields = tokenizer.tokenize(line);
		assertEquals(34, fields.getFieldCount());
		// return tokenizer;
	}

}
