package au.com.maxcheung.fileingestor.flatfilereader;

import static au.com.maxcheung.fileingestor.utils.LogUtils.futureLogMsg;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import au.com.maxcheung.fileingestor.csv.CsvReader;
import au.com.maxcheung.fileingestor.future.model.FlatFileSpec;
import au.com.maxcheung.fileingestor.future.model.FutureTransaction;

/**
 * FlatFileReader to load fixed length file.
 * 
 * @author Max Cheung <max.cheung@optusnet.com.au>
 */

@Service
public class FlatFileReaderImpl extends BaseReader implements FlatFileReader {

    private static final String FUTURE_FILESPEC_CSV = "future-filespec.csv";

    private static final Logger LOGGER = LoggerFactory.getLogger(FlatFileReaderImpl.class);

    private final CsvReader csvReader;
    private final FlatFileItemReader<FutureTransaction> reader;

    public FlatFileReaderImpl() throws IOException {
        csvReader = new CsvReader(FlatFileSpec.class);
        DefaultLineMapper<FutureTransaction> lineMapper = getLineMapper();
        reader = (FlatFileItemReader<FutureTransaction>) getReader(lineMapper);
    }

    /* (non-Javadoc)
     * @see au.com.maxcheung.fileingestor.flatfilereader.FlatFileReader#read(java.lang.String)
     */
    @Override
    public List<FutureTransaction> read(String filename) throws UnexpectedInputException, ParseException, Exception {
        getLineMapper();
        reader.setResource(new FileSystemResource(getFile(filename)));
        reader.open(new ExecutionContext());
        List<FutureTransaction> rows = readData(reader);
        LOGGER.info(futureLogMsg("Processed file : {}, rows : {}."), filename, rows.size());
        return rows;
    }

    /* (non-Javadoc)
     * @see au.com.maxcheung.fileingestor.flatfilereader.FlatFileReader#getLineMapper()
     */
    @Override
    public DefaultLineMapper<FutureTransaction> getLineMapper() throws IOException {
        InputStream fileSpecIS = new ClassPathResource(FUTURE_FILESPEC_CSV).getInputStream();
        DefaultLineMapper<FutureTransaction> mapper = new DefaultLineMapper<FutureTransaction>();
        mapper.setLineTokenizer(getTokenizer(fileSpecIS));
        mapper.setFieldSetMapper(getFieldSetMapper());
        return mapper;
    }

    /* (non-Javadoc)
     * @see au.com.maxcheung.fileingestor.flatfilereader.FlatFileReader#getTokenizer(java.io.InputStream)
     */
    @Override
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
