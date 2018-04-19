package au.com.maxcheung.futureclearer.flatfile;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.item.file.LineMapper;

import au.com.maxcheung.futureclearer.csv.CsvReader;
import au.com.maxcheung.futureclearer.model.FlatFileSpec;
import au.com.maxcheung.futureclearer.model.FutureTransaction;
import au.com.maxcheung.futureclearer.model.FutureTransactionDTO;
import au.com.maxcheung.futureclearer.model.FutureTransactionSummary;
import au.com.maxcheung.futureclearer.transform.FutureTransactionSummaryTransformer;
import au.com.maxcheung.futureclearer.transform.FutureTransactionTransformer;

public class FutureTransactionSummaryTransformerTest {

    private static final BigDecimal TRANSACTION_PRICE_VALUE = new BigDecimal("9250.0000000");

    private static final String TRANSACTION_PRICE = "000092500000000";

    private static final BigDecimal COMMISION_VALUE = new BigDecimal("0000000000.00");

    private static final BigDecimal CLEARING_FEE_VALUE = new BigDecimal("0000000000.30");

    private static final BigDecimal EXCHANGE_BROKER_FEE_VALUE = new BigDecimal("0000000000.60");

    private static final String COMMISSION = "000000000000";

    private static final String CLEARING_FEE = "000000000030";

    private static final String BROKER_FEE = "000000000060";

    private static final Long QUANTITY_SHORT = Long.valueOf(0);

    private static final Long QUANTITY_LONG = Long.valueOf(1L);

    private static final String EXPIRATION_DATE = "20100910";

    private static final String SYMBOL = "NK";

    private static final String EXCHANGE_CODE = "SGX";

    private static final String PRODUCT_GROUP_CODE = "FU";

    private static final String SUB_ACCOUNT_NUMBER = "0001";

    private static final String ACCOUNT_NUMBER = "0002";

    private static final String CLIENT_NUMBER = "4321";

    private static final String CLIENT_TYPE = "CL";

    private static final String TRANSACTION_ROW = "315CL  432100020001SGXDC FUSGX NK    20100910JPY01B 0000000001 0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012380     688032000092500000000             O";

    protected static final String FILESPEC_FILEPATH = "src\\test\\resources\\filespec\\";

    private static final String RECORD_CODE = "315";
    private FutureTransactionTransformer transformer;
    private FlatFileReader flatFileReader;
    private LineMapper<FutureTransactionDTO> lineMapper;
    private FutureTransactionDTO futureTransactionDTO;
    private FutureTransactionSummaryTransformer futureTransactionSummaryTransformer;

    @Before
    public void setup() throws Exception {
        transformer = new FutureTransactionTransformer();
        futureTransactionSummaryTransformer = new FutureTransactionSummaryTransformer();
        flatFileReader = new FlatFileReader(new CsvReader(FlatFileSpec.class));
        lineMapper = flatFileReader.getLineMapper(FILESPEC_FILEPATH + "future-filespec.csv");
        futureTransactionDTO = lineMapper.mapLine(TRANSACTION_ROW, 0);
    }

    @Test
    public void shouldTransformFutureTransaction() throws Exception {
        FutureTransaction futureTransaction1 = transformer.transform(futureTransactionDTO);
        FutureTransaction futureTransaction2 = transformer.transform(futureTransactionDTO);
        List<FutureTransaction> txns = new ArrayList<FutureTransaction>();
        txns.add(futureTransaction1);
        txns.add(futureTransaction2);
        List<FutureTransactionSummary> result = futureTransactionSummaryTransformer.transform(txns);
        assertEquals(1, result.size());
        String clientInfo = Arrays.asList(CLIENT_TYPE, CLIENT_NUMBER, ACCOUNT_NUMBER, SUB_ACCOUNT_NUMBER).stream()
                .collect(Collectors.joining("_"));
        String productInfo = Arrays.asList(PRODUCT_GROUP_CODE, EXCHANGE_CODE, SYMBOL, EXPIRATION_DATE).stream()
                .collect(Collectors.joining("_"));
        assertEquals(clientInfo, result.get(0).getClientInfo());
        assertEquals(productInfo, result.get(0).getProductInfo());
        assertEquals(new BigDecimal("2"), result.get(0).getTotalTransactionAmount());
    }

    @Test
    public void shouldTransformMulipleAccounts() throws Exception {
        FutureTransaction futureTransaction1 = transformer.transform(futureTransactionDTO);
        FutureTransaction futureTransaction2 = transformer.transform(futureTransactionDTO);
        futureTransaction2.setAccountNumber(ACCOUNT_NUMBER + "2");

        List<FutureTransaction> txns = new ArrayList<FutureTransaction>();
        txns.add(futureTransaction1);
        txns.add(futureTransaction2);
        List<FutureTransactionSummary> result = futureTransactionSummaryTransformer.transform(txns);
        assertEquals(2, result.size());
    }

}
