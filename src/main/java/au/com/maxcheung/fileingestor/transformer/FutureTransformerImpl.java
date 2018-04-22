package au.com.maxcheung.fileingestor.transformer;


import static au.com.maxcheung.fileingestor.utils.LogUtils.futureLogMsg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import au.com.maxcheung.fileingestor.future.model.FutureTransaction;
import au.com.maxcheung.fileingestor.future.model.FutureTransactionSummary;



@Service
public class FutureTransformerImpl implements FutureTransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FutureTransformerImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * au.com.maxcheung.futureclearer.transform.FutureTransformer#transform(java.
     * util.List)
     */
    @Override
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
        LOGGER.info(futureLogMsg("Transformed input rows : {}  summary rows : {} "), futureTransactions.size(), summaryList.size());
   return summaryList;
    }

}
