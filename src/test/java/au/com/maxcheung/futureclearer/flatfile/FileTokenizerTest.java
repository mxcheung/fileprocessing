package au.com.maxcheung.futureclearer.flatfile;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.core.io.FileSystemResource;

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
public class FileTokenizerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileTokenizerTest.class);

    private CsvReader fileSpecCSVReader;
    protected static final String FILESPEC_FILEPATH = "src\\test\\resources\\filespec\\";

    @Before
    public void setup() {
        fileSpecCSVReader = new CsvReader(FlatFileSpec.class);
    }

    // https://www.programcreek.com/java-api-examples/?api=org.springframework.batch.item.file.mapping.DefaultLineMapper

    /**
     * Tokenize fixed length string
     * 
     * @throws Exception
     */
    @Test
    public void shouldTokenize() throws Exception {
        FixedLengthTokenizer tokenizer = getTokenizer(FILESPEC_FILEPATH + "future-filespec.csv");

        String line = "315CL  432100020001SGXDC FUSGX NK    20100910JPY01B 0000000001 0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012380     688032000092500000000             O";
        FieldSet fields = tokenizer.tokenize(line);
        assertEquals(34, fields.getFieldCount());

        DefaultLineMapper<FutureTransaction> lineMapper = new DefaultLineMapper<FutureTransaction>();
        // FlatFileItemReader<Ticket> reader = new FlatFileItemReader<>();
        BeanWrapperFieldSetMapper<FutureTransaction> wrapper = new BeanWrapperFieldSetMapper<FutureTransaction>();
        wrapper.setTargetType(FutureTransaction.class);
        lineMapper.setFieldSetMapper(wrapper);
        lineMapper.setLineTokenizer(tokenizer);
        // reader.setLineMapper(lineMapper);
        FutureTransaction ticket = lineMapper.mapLine(line, 0);
        assertEquals("CL", ticket.getClientType());
        assertEquals(Long.valueOf(1), ticket.getQuantityLong());

        FlatFileItemReader<FutureTransaction> r = new FlatFileItemReader<>();
        String dataFile = FILESPEC_FILEPATH + "datafile.txt";
        File file = new File(dataFile);
        r.setResource(new FileSystemResource(file));
        r.setLineMapper(lineMapper);
        r.open(new ExecutionContext());
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        List<FutureTransaction> tickets = new ArrayList<>();
        FutureTransaction store = null;
        int lineNo = 1;
        do {

            store = r.read();

            if (store != null) {
                Set<ConstraintViolation<FutureTransaction>> violations = validator.validate(store);
                for (ConstraintViolation<FutureTransaction> violation : violations) {
                    LOGGER.error("line: " + lineNo + " : " + violation.getPropertyPath() + " : "
                            + violation.getInvalidValue() + " : " + violation.getMessage());
                }
                tickets.add(store);
            }
            lineNo++;
        } while (store != null);

        assertEquals(717, tickets.size());

    }

    private FixedLengthTokenizer getTokenizer(String filePath) throws FileNotFoundException, IOException {
        List<FlatFileSpec> rows = fileSpecCSVReader.readCsv(filePath);
        assertEquals(34, rows.size());
        FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();
        List<String> columnNames = rows.stream().map(e -> e.getColumnName()).collect(Collectors.toList());
        tokenizer.setNames(columnNames.toArray(new String[0]));
        List<Range> rangeList = rows.stream().map(e -> new Range(e.getStart(), e.getEnd()))
                .collect(Collectors.toList());
        tokenizer.setColumns(rangeList.toArray(new Range[0]));
        return tokenizer;
    }

    @Test
    public void shouldTokenizexxx() throws FileNotFoundException, IOException {

        String rfiMasterFilePath = FILESPEC_FILEPATH + "future-filespec.csv";
        List<FlatFileSpec> rows = fileSpecCSVReader.readCsv(rfiMasterFilePath);
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
        for (FlatFileSpec row : rows) {
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
