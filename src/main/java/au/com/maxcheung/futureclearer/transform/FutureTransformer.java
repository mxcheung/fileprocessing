package au.com.maxcheung.futureclearer.transform;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import au.com.maxcheung.futureclearer.model.FutureTransaction;
import au.com.maxcheung.futureclearer.model.FutureTransactionSummary;

@Service
public class FutureTransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FutureTransformer.class);

    public FutureTransformer() {
    }

    public List<FutureTransactionSummary> transform(List<FutureTransaction> futureTransactions) {
        List<FutureTransactionSummary> summaryList = new ArrayList<FutureTransactionSummary>();
        Map<String, List<FutureTransaction>> transactionsByGroup = futureTransactions.stream()
                .collect(Collectors.groupingBy(FutureTransaction::getCompositeKey));
        transactionsByGroup.forEach((k, v) -> {
            BigDecimal totalTransactionAmount = v.stream().map(FutureTransaction::getTotalTransactionAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            FutureTransactionSummary summary = new FutureTransactionSummary();
            summary.setClientInfo(v.get(0).getClientInfoKey());
            summary.setProductInfo(v.get(0).getProductInfoKey());
            summary.setTransactionInfo(totalTransactionAmount.toString());
            summary.setTotalTransactionAmount(totalTransactionAmount);
            summaryList.add(summary);
        });
        LOGGER.info("Transformed input rows : {}  summary rows : {} ",  futureTransactions.size(), summaryList.size());

        return summaryList;
    }

}
