package au.com.maxcheung.fileingestor.transformer;

import java.util.List;

import au.com.maxcheung.fileingestor.future.model.FutureTransaction;
import au.com.maxcheung.fileingestor.future.model.FutureTransactionSummary;

public interface FutureTransformer {

    List<FutureTransactionSummary> transform(List<FutureTransaction> futureTransactions);

}
