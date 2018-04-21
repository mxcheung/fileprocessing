package au.com.maxcheung.futureclearer.future;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.maxcheung.futureclearer.flatfile.FlatFileReader;
import au.com.maxcheung.futureclearer.model.FutureTransaction;
import au.com.maxcheung.futureclearer.model.FutureTransactionSummary;
import au.com.maxcheung.futureclearer.transform.FutureTransformer;
import au.com.maxcheung.futureclearer.validate.FutureValidator;

/**
 * Lookup service supports the maintenance of the lookup repository. Main usage:
 * 1. Fetch lookups based on a shopping 2. Refresh lookup, refreshes repository
 * using file based resource.
 * 
 * @author Max Cheung <max.cheung@lonsec.com.au>
 */

@Service
public class FutureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FutureService.class);

    private final FlatFileReader flatFileReader;
    private final FutureTransformer futureTransactionSummaryTransformer;
    private final FutureValidator futureValidator;
    private final FutureWriter writer;

    @Autowired
    FutureService(FlatFileReader flatFileReader, FutureValidator futureValidator, FutureTransformer futureTransformer, FutureWriter futureWriter) {
        this.flatFileReader = flatFileReader;
        this.futureValidator = futureValidator;
        this.futureTransactionSummaryTransformer = futureTransformer;
        this.writer = futureWriter;

    }

    public List<FutureTransactionSummary> lookupLoad(String dataFile, String reportFile)
            throws FileLoadException {

        LOGGER.info("Data File Name : {}", dataFile);
        LOGGER.info("Report File Name : {}", reportFile);

        List<FutureTransactionSummary> result = new ArrayList<FutureTransactionSummary>();
        try {
            LOGGER.info("Read Transactions");
            List<FutureTransaction> transactions = flatFileReader.read(dataFile);
            LOGGER.info("Validate Transactions");
            futureValidator.validate(transactions);
            LOGGER.info("Transform Transactions");
            result = futureTransactionSummaryTransformer.transform(transactions);
            LOGGER.info("Writing summary report");
            writer.write(result, reportFile);

        } catch (Exception e) {
            throw new FileLoadException(e.getMessage());
        }
        return result;
    }

}
