package au.com.maxcheung.futureclearer.flatfile;

import java.util.List;

import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import au.com.maxcheung.futureclearer.model.FutureTransaction;

public interface FlatFileReader {

    List<FutureTransaction> read(String filespec, String filename) throws UnexpectedInputException, ParseException, Exception;

}
