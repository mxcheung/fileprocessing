package au.com.maxcheung.fileingestor.transformer;

import java.util.List;

import au.com.maxcheung.fileingestor.future.model.FutureTransaction;
import au.com.maxcheung.fileingestor.future.model.FutureTransactionSummary;

public interface FutureTransformer {

    /**
     * @param futureTransactions future transactions records to be transformed to summary records.
     * @return summary records group by client, product codes. 
     */
    List<FutureTransactionSummary> transform(List<FutureTransaction> futureTransactions);

}
