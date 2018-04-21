package au.com.maxcheung.futureclearer.future;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.maxcheung.futureclearer.csv.CsvWriter;
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
public class LookupService {

    private final FlatFileReader flatFileReader;
    private FutureTransformer futureTransactionSummaryTransformer;
    private FutureValidator futureValidator;
    private CsvWriter writer;

    @Autowired
    LookupService(FlatFileReader flatFileReader, FutureValidator futureValidator) {
        this.flatFileReader = flatFileReader;
        futureTransactionSummaryTransformer = new FutureTransformer();
//        futureValidator = new FutureValidator();
        writer = new CsvWriter(FutureTransactionSummary.class);

    }

    public List<FutureTransactionSummary> lookupLoad(FutureTransactionLoadRequest lookupLoadRequest) throws FileLoadException {
        List<FutureTransactionSummary> result = new ArrayList<FutureTransactionSummary>();
        try {
            List<FutureTransaction> transactions = flatFileReader.read(lookupLoadRequest.getSpecfileName(),
                    lookupLoadRequest.getDatafileName());
            futureValidator.validate(transactions);
            result = futureTransactionSummaryTransformer.transform(transactions);
            String reportfileName = lookupLoadRequest.getReportfileName();
            if (StringUtils.isNotEmpty(reportfileName)) {
                writer.write(result, reportfileName);
            }

        } catch (Exception e) {
            throw new FileLoadException(e.getMessage());
        }
        return result;
    }

}
