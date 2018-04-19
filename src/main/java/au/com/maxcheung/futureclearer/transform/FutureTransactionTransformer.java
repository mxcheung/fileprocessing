package au.com.maxcheung.futureclearer.transform;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;

import au.com.maxcheung.futureclearer.model.FutureTransaction;
import au.com.maxcheung.futureclearer.model.FutureTransactionDTO;

public class FutureTransactionTransformer {

    private static final int NEGATIVE_ONE = -1;
    private static final int FEE_SCALE = 2;
    private static final int PRICE_SCALE = 7;
    private DozerBeanMapper mapper;

    public FutureTransactionTransformer() {
        mapper = new DozerBeanMapper();
    }

    public FutureTransaction transform(FutureTransactionDTO source) {
        FutureTransaction dest = new FutureTransaction();
        mapper.map(source, dest);
        dest.setExchangeBrokerFee(convert(source.getExchangeBrokerFee(), FEE_SCALE));
        dest.setClearingFee(convert(source.getClearingFee(), FEE_SCALE));
        dest.setCommission(convert(source.getCommission(), FEE_SCALE));
        dest.setTransactionPrice(convert(source.getTransactionPrice(), PRICE_SCALE));

        dest.setQuantityLong(getQty(source.getQuantityLongSign(), source.getQuantityLong()));
        dest.setQuantityShort(getQty(source.getQuantityShortSign(), source.getQuantityShort()));
        return dest;
    }

    private Long getQty(String qtyLongSign, Long qty) {
        return StringUtils.equalsIgnoreCase("-", qtyLongSign) ? NEGATIVE_ONE * qty : qty;
    }

    private BigDecimal convert(String input, int scale) {
        StringBuilder builder = new StringBuilder(input);
        builder.insert(builder.length() - scale, ".");
        return new BigDecimal(builder.toString());
    }

}
