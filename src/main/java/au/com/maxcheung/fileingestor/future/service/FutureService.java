package au.com.maxcheung.fileingestor.future.service;

import static au.com.maxcheung.fileingestor.utils.LogUtils.futureLogMsg;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.maxcheung.fileingestor.flatfilereader.FlatFileReader;
import au.com.maxcheung.fileingestor.future.exception.FileLoadException;
import au.com.maxcheung.fileingestor.future.model.FutureTransaction;
import au.com.maxcheung.fileingestor.future.model.FutureTransactionSummary;
import au.com.maxcheung.fileingestor.transformer.FutureTransformer;
import au.com.maxcheung.fileingestor.validator.FutureValidator;
import au.com.maxcheung.fileingestor.writer.FutureWriter;

@Service
public class FutureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FutureService.class);

    private final FlatFileReader flatFileReader;
    private final FutureTransformer futureTransactionSummaryTransformer;
    private final FutureValidator futureValidator;
    private final FutureWriter writer;

    @Autowired
    FutureService(FlatFileReader flatFileReader, FutureValidator futureValidator, FutureTransformer futureTransformer,
            FutureWriter futureWriter) {
        this.flatFileReader = flatFileReader;
        this.futureValidator = futureValidator;
        this.futureTransactionSummaryTransformer = futureTransformer;
        this.writer = futureWriter;

    }

    public List<FutureTransactionSummary> lookupLoad(String dataFile, String reportFile) throws FileLoadException {
        LOGGER.info(futureLogMsg("Data File Name : {}"), dataFile);
        LOGGER.info(futureLogMsg("Report File Name : {}"), reportFile);
        List<FutureTransactionSummary> result = new ArrayList<FutureTransactionSummary>();
        try {
            LOGGER.info(futureLogMsg("Read Transactions"));
            List<FutureTransaction> transactions = flatFileReader.read(dataFile);
            LOGGER.info(futureLogMsg("Validate Transactions"));
            futureValidator.validate(transactions);
            LOGGER.info(futureLogMsg("Transform Transactions"));
            result = futureTransactionSummaryTransformer.transform(transactions);
            LOGGER.info(futureLogMsg("Writing summary report"));
            writer.write(result, reportFile);

        } catch (Exception e) {
            throw new FileLoadException(e.getMessage());
        }
        return result;
    }

}
