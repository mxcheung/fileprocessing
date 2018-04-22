package au.com.maxcheung.fileingestor.flatfilereader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;

import au.com.maxcheung.fileingestor.future.model.FutureTransaction;

public interface FlatFileReader {

    List<FutureTransaction> read(String filename) throws UnexpectedInputException, ParseException, Exception;

    DefaultLineMapper<FutureTransaction> getLineMapper() throws IOException;

    FixedLengthTokenizer getTokenizer(InputStream filePath) throws FileNotFoundException, IOException;

}
