package au.com.maxcheung.futureclearer.flatfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import au.com.maxcheung.futureclearer.csv.CsvReader;
import au.com.maxcheung.futureclearer.model.FlatFileSpec;
import au.com.maxcheung.futureclearer.model.FutureTransaction;

/**
 * FlatFileReader to load fixed length file.
 * 
 * @author Max Cheung <max.cheung@optusnet.com.au>
 */

@Service
public class FlatFileReaderImpl extends BaseReader implements FlatFileReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlatFileReaderImpl.class);

    private CsvReader csvReader;
    private DefaultLineMapper<FutureTransaction> lineMapper;
    private FlatFileItemReader<FutureTransaction> reader;

    public FlatFileReaderImpl() throws IOException {
        this.csvReader = new CsvReader(FlatFileSpec.class);
        lineMapper = getLineMapper();
        reader = (FlatFileItemReader<FutureTransaction>) getReader(lineMapper);
    }

    @Override
    public List<FutureTransaction> read(String filename)
            throws UnexpectedInputException, ParseException, Exception {
        getLineMapper();
        reader.setResource(new FileSystemResource(getFile(filename)));
        reader.open(new ExecutionContext());
        List<FutureTransaction> rows = readData();
        LOGGER.info("Processed file : {}, rows : {}.", filename, rows.size());
        return rows;
    }

    public ItemReader<FutureTransaction> getReader(LineMapper<FutureTransaction> futureLineMapper) {
        FlatFileItemReader<FutureTransaction> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setLineMapper(futureLineMapper);
        return flatFileItemReader;
    }

    @Override
    public DefaultLineMapper<FutureTransaction> getLineMapper() throws IOException {
        InputStream cpfileSpec = new ClassPathResource("future-filespec.csv").getInputStream();
        lineMapper = new DefaultLineMapper<FutureTransaction>();
        lineMapper.setLineTokenizer(getTokenizer(cpfileSpec));
        lineMapper.setFieldSetMapper(getFieldSetMapper());
        return lineMapper;
    }

    
    private BeanWrapperFieldSetMapper<FutureTransaction> getFieldSetMapper() {
        BeanWrapperFieldSetMapper<FutureTransaction> wrapper = new BeanWrapperFieldSetMapper<FutureTransaction>();
        wrapper.setTargetType(FutureTransaction.class);
        return wrapper;
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> readData() throws Exception {
        List<T> txns = new ArrayList<>();
        T t = null;
        do {
            t = (T) reader.read();
            if (t != null) {
                txns.add(t);
            }
        } while (t != null);
        return txns;

    }

    private File getFile(String fileName) {
        return new File(fileName);
    }


    public FixedLengthTokenizer getTokenizer(InputStream filePath) throws FileNotFoundException, IOException {
        List<FlatFileSpec> rows = csvReader.readCsv(filePath);
        FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();
        List<String> columnNames = rows.stream().map(e -> e.getColumnName()).collect(Collectors.toList());
        tokenizer.setNames(columnNames.toArray(new String[0]));
        List<Range> rangeList = rows.stream().map(e -> new Range(e.getStart(), e.getEnd()))
                .collect(Collectors.toList());
        tokenizer.setColumns(rangeList.toArray(new Range[0]));
        return tokenizer;
    }

}
