package au.com.maxcheung.futureclearer.flatfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.core.io.FileSystemResource;

import au.com.maxcheung.futureclearer.csv.CsvReader;
import au.com.maxcheung.futureclearer.model.FlatFileSpec;
import au.com.maxcheung.futureclearer.model.FutureTransactionDTO;

/**
 * FlatFileReader to load fixed length file.
 * 
 * @author Max Cheung <max.cheung@optusnet.com.au>
 */

public class FlatFileReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlatFileReader.class);

    private CsvReader csvReader;
    private DefaultLineMapper<FutureTransactionDTO> lineMapper;
    private FlatFileItemReader<FutureTransactionDTO> r;

    public FlatFileReader(CsvReader csvReader) {

        this.csvReader = csvReader;
        // fileSpecCSVReader = new CsvReader(FlatFileSpecRow.class);
        lineMapper = getLineMapper();

        r = new FlatFileItemReader<>();
        r.setLineMapper(lineMapper);
    }

    public <T> List<T> read(String filespec, String filename)
            throws UnexpectedInputException, ParseException, Exception {
        getLineMapper(filespec);
        r.setResource(new FileSystemResource(getFile(filename)));
        r.open(new ExecutionContext());
        return readData();
    }

    private DefaultLineMapper<FutureTransactionDTO> getLineMapper() {
        lineMapper = new DefaultLineMapper<FutureTransactionDTO>();
        lineMapper.setFieldSetMapper(getFieldSetMapper());
        return lineMapper;
    }

    private BeanWrapperFieldSetMapper<FutureTransactionDTO> getFieldSetMapper() {
        BeanWrapperFieldSetMapper<FutureTransactionDTO> wrapper = new BeanWrapperFieldSetMapper<FutureTransactionDTO>();
        wrapper.setTargetType(FutureTransactionDTO.class);
        return wrapper;
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> readData() throws Exception {
        List<T> tickets = new ArrayList<>();
        T store = null;
        do {
            store = (T) r.read();
            if (store != null) {
                tickets.add(store);
            }
        } while (store != null);
        return tickets;
    }

    private File getFile(String fileName) {
        return new File(fileName);
    }

    public LineMapper<FutureTransactionDTO> getLineMapper(String filespec) throws FileNotFoundException, IOException {
        lineMapper.setLineTokenizer(getTokenizer(filespec));
        return lineMapper;
    }

    public FixedLengthTokenizer getTokenizer(String filePath) throws FileNotFoundException, IOException {
        List<FlatFileSpec> rows = csvReader.readCsv(filePath);
        FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();
        List<String> columnNames = rows.stream().map(e -> e.getColumnName()).collect(Collectors.toList());
        tokenizer.setNames(columnNames.toArray(new String[0]));
        List<Range> rangeList = rows.stream().map(e -> new Range(e.getStart(), e.getEnd()))
                .collect(Collectors.toList());
        tokenizer.setColumns(rangeList.toArray(new Range[0]));
        return tokenizer;
    }

    public FutureTransactionDTO readLine(String line) throws Exception {
        return lineMapper.mapLine(line, 0);
    }

}
