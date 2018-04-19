package au.com.maxcheung.futureclearer.transform;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import au.com.maxcheung.futureclearer.model.FutureTransaction;
import au.com.maxcheung.futureclearer.model.FutureTransactionSummary;

public class FutureTransactionSummaryTransformer {

    public FutureTransactionSummaryTransformer() {
    }

    public List<FutureTransactionSummary> transform(List<FutureTransaction> futureTransactions) {
        Map<String, List<FutureTransaction>> transactionsByGroup = futureTransactions.stream()
                .collect(Collectors.groupingBy(FutureTransaction::getCompositeKey));

        List<FutureTransactionSummary> summaryList = new ArrayList<FutureTransactionSummary>();
        transactionsByGroup.forEach((k, v) -> {
            List<FutureTransaction> transactions = v;
            String clientInfo = transactions.get(0).getClientInfo();
            String productInfo = transactions.get(0).getProductInfo();
            BigDecimal totalTransactionAmount = transactions.stream().map(FutureTransaction::getTotalAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            FutureTransactionSummary summary = new FutureTransactionSummary();
            summary.setClientInfo(clientInfo);
            summary.setProductInfo(productInfo);
            summary.setTransactionInfo(totalTransactionAmount.toString());
            summary.setTotalTransactionAmount(totalTransactionAmount);
            summaryList.add(summary);
        });
        return summaryList;
    }

}
