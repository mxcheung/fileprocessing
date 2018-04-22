package au.com.maxcheung.fileingestor.future.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Summary Report.
 * 
 * @author Max Cheung <max.cheung@optusnet.com.au>
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "clientInfo", "productInfo", "transactionInfo" })
public class FutureTransactionSummary {

    private String clientInfo;
    private String productInfo;
    private String transactionInfo;
    private BigDecimal totalTransactionAmount;

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public String getTransactionInfo() {
        return transactionInfo;
    }

    public void setTransactionInfo(String transactionInfo) {
        this.transactionInfo = transactionInfo;
    }

    @JsonIgnore
    public BigDecimal getTotalTransactionAmount() {
        return totalTransactionAmount;
    }

    public void setTotalTransactionAmount(BigDecimal totalTransactionAmount) {
        this.totalTransactionAmount = totalTransactionAmount;
    }

}
