package au.com.maxcheung.fileingestor.writer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FutureWriter {

    <T> void write(List<T> rows, String filename) throws FileNotFoundException, IOException;

}
