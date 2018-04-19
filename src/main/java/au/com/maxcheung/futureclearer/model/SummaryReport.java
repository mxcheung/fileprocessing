package au.com.maxcheung.futureclearer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Summary Report.
 * 
 * @author Max Cheung <max.cheung@optusnet.com.au>
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "clientInfo", "productInfo", "totalTransactionAmount" })
public class SummaryReport {

    private String clientInfo;
    private String productInfo;
    private String totalTransactionAmount;

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

    public String getTotalTransactionAmount() {
        return totalTransactionAmount;
    }

    public void setTotalTransactionAmount(String totalTransactionAmount) {
        this.totalTransactionAmount = totalTransactionAmount;
    }

}
