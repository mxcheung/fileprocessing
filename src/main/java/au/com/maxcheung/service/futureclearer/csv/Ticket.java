package au.com.maxcheung.service.futureclearer.csv;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "recordCode", "clientType", "clientNumber" })
public class Ticket {

    @Pattern(regexp = "[3][1][5]")
    private String recordCode;
    private String clientType;

    @Digits(integer = 4, fraction = 0)
    private String clientNumber;

    @Digits(integer = 4, fraction = 0)
    private String accountNumber;

    @Digits(integer = 4, fraction = 0)
    private String subAccountNumber;

    private String oppositePartyCode;
    private String productGroupCode;
    private String exchangeCode;
    private String symbol;

    @Pattern(regexp = "[0-9]{8}")
    private String expirationDate;

    private String currencyCode;
    private String movementCode;

    @Size(min = 1, max = 1)
    private String buySellCode;
    private String quantityLongSign;

    @Digits(integer = 10, fraction = 0)
    private String quantityLong;

    private String quantityShortSign;

    @Digits(integer = 10, fraction = 0)
    private String quantityShort;

    @Digits(integer = 12, fraction = 2)
    private String exchangeBrokerFee;

    private String exchangeBrokerFeeDC;
    private String exchangeBrokerFeeCurCode;
    private String clearingFee;
    private String clearingFeeDC;
    private String clearingFeeCurCode;
    private String commission;
    private String commissionDC;
    private String commissionCurCode;

    @Pattern(regexp = "[0-9]{8}")
    private String transactionDate;

    @Digits(integer = 6, fraction = 0)
    private String futureReference;

    private String ticketNumber;

@Digits(integer = 6, fraction = 0)
    private String externalNumber;

    @Digits(integer = 15, fraction = 7)
    private String transactionPrice;
    private String traderInitials;
    private String oppositeTraderId;
    private String openCloseCode;

    public String getRecordCode() {
        return recordCode;
    }

    public void setRecordCode(String recordCode) {
        this.recordCode = recordCode;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSubAccountNumber() {
        return subAccountNumber;
    }

    public void setSubAccountNumber(String subAccountNumber) {
        this.subAccountNumber = subAccountNumber;
    }

    public String getOppositePartyCode() {
        return oppositePartyCode;
    }

    public void setOppositePartyCode(String oppositePartyCode) {
        this.oppositePartyCode = oppositePartyCode;
    }

    public String getProductGroupCode() {
        return productGroupCode;
    }

    public void setProductGroupCode(String productGroupCode) {
        this.productGroupCode = productGroupCode;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getMovementCode() {
        return movementCode;
    }

    public void setMovementCode(String movementCode) {
        this.movementCode = movementCode;
    }

    public String getBuySellCode() {
        return buySellCode;
    }

    public void setBuySellCode(String buySellCode) {
        this.buySellCode = buySellCode;
    }

    public String getQuantityLongSign() {
        return quantityLongSign;
    }

    public void setQuantityLongSign(String quantityLongSign) {
        this.quantityLongSign = quantityLongSign;
    }

    public String getQuantityLong() {
        return quantityLong;
    }

    public void setQuantityLong(String quantityLong) {
        this.quantityLong = quantityLong;
    }

    public String getQuantityShortSign() {
        return quantityShortSign;
    }

    public void setQuantityShortSign(String quantityShortSign) {
        this.quantityShortSign = quantityShortSign;
    }

    public String getQuantityShort() {
        return quantityShort;
    }

    public void setQuantityShort(String quantityShort) {
        this.quantityShort = quantityShort;
    }

    public String getExchangeBrokerFee() {
        return exchangeBrokerFee;
    }

    public void setExchangeBrokerFee(String exchangeBrokerFee) {
        this.exchangeBrokerFee = exchangeBrokerFee;
    }

    public String getExchangeBrokerFeeDC() {
        return exchangeBrokerFeeDC;
    }

    public void setExchangeBrokerFeeDC(String exchangeBrokerFeeDC) {
        this.exchangeBrokerFeeDC = exchangeBrokerFeeDC;
    }

    public String getExchangeBrokerFeeCurCode() {
        return exchangeBrokerFeeCurCode;
    }

    public void setExchangeBrokerFeeCurCode(String exchangeBrokerFeeCurCode) {
        this.exchangeBrokerFeeCurCode = exchangeBrokerFeeCurCode;
    }

    public String getClearingFee() {
        return clearingFee;
    }

    public void setClearingFee(String clearingFee) {
        this.clearingFee = clearingFee;
    }

    public String getClearingFeeDC() {
        return clearingFeeDC;
    }

    public void setClearingFeeDC(String clearingFeeDC) {
        this.clearingFeeDC = clearingFeeDC;
    }

    public String getClearingFeeCurCode() {
        return clearingFeeCurCode;
    }

    public void setClearingFeeCurCode(String clearingFeeCurCode) {
        this.clearingFeeCurCode = clearingFeeCurCode;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getCommissionDC() {
        return commissionDC;
    }

    public void setCommissionDC(String commissionDC) {
        this.commissionDC = commissionDC;
    }

    public String getCommissionCurCode() {
        return commissionCurCode;
    }

    public void setCommissionCurCode(String commissionCurCode) {
        this.commissionCurCode = commissionCurCode;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getFutureReference() {
        return futureReference;
    }

    public void setFutureReference(String futureReference) {
        this.futureReference = futureReference;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getExternalNumber() {
        return externalNumber;
    }

    public void setExternalNumber(String externalNumber) {
        this.externalNumber = externalNumber;
    }

    public String getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(String transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    public String getTraderInitials() {
        return traderInitials;
    }

    public void setTraderInitials(String traderInitials) {
        this.traderInitials = traderInitials;
    }

    public String getOppositeTraderId() {
        return oppositeTraderId;
    }

    public void setOppositeTraderId(String oppositeTraderId) {
        this.oppositeTraderId = oppositeTraderId;
    }

    public String getOpenCloseCode() {
        return openCloseCode;
    }

    public void setOpenCloseCode(String openCloseCode) {
        this.openCloseCode = openCloseCode;
    }

}
