package au.com.maxcheung.futureclearer.transform;

import java.util.List;

import au.com.maxcheung.futureclearer.model.FutureTransaction;
import au.com.maxcheung.futureclearer.model.FutureTransactionSummary;

public interface FutureTransformer {

    List<FutureTransactionSummary> transform(List<FutureTransaction> futureTransactions);

}
