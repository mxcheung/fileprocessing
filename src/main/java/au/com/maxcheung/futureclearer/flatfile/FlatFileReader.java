package au.com.maxcheung.futureclearer.flatfile;

import java.io.IOException;
import java.util.List;

import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;

import au.com.maxcheung.futureclearer.model.FutureTransaction;

public interface FlatFileReader {

    List<FutureTransaction> read(String filename) throws UnexpectedInputException, ParseException, Exception;

    DefaultLineMapper<FutureTransaction> getLineMapper() throws IOException;

}
