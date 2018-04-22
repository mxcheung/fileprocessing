package au.com.maxcheung.futureclearer.write;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.maxcheung.futureclearer.csv.CsvWriter;
import au.com.maxcheung.futureclearer.model.FutureTransactionSummary;

@Service
public class FutureWriterImpl implements FutureWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FutureWriterImpl.class);

    private CsvWriter writer;

    @Autowired
    FutureWriterImpl() {
        writer = new CsvWriter(FutureTransactionSummary.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * au.com.maxcheung.futureclearer.future.writer.FutureWriter#write(java.util.
     * List, java.lang.String)
     */
    @Override
    public <T> void write(List<T> rows, String filename) throws FileNotFoundException, IOException {
        writer.write(rows, filename);
        logger().info("Written rows : {} to file : {}.", rows.size(), filename);
    }

    Logger logger() {
        return LOGGER;
    }

}
