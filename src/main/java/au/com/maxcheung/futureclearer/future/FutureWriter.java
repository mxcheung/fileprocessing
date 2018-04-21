package au.com.maxcheung.futureclearer.future;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.maxcheung.futureclearer.csv.CsvWriter;
import au.com.maxcheung.futureclearer.model.FutureTransactionSummary;

/**
 * Lookup service supports the maintenance of the lookup repository. Main usage:
 * 1. Fetch lookups based on a shopping 2. Refresh lookup, refreshes repository
 * using file based resource.
 * 
 * @author Max Cheung <max.cheung@lonsec.com.au>
 */

@Service
public class FutureWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FutureWriter.class);

    private CsvWriter writer;

    @Autowired
    FutureWriter() {
        writer = new CsvWriter(FutureTransactionSummary.class);
    }

    public <T> void write(List<T> rows, String filename) throws FileNotFoundException, IOException {
        writer.write(rows, filename);
        logger().info("Written rows : {} to file : {}.", rows.size(), filename);
    }
    
    Logger logger() {
        return LOGGER;
    }


}
