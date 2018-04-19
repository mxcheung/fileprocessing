package au.com.maxcheung.futureclearer.flatfile;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.item.file.LineMapper;

import au.com.maxcheung.futureclearer.csv.CsvReader;
import au.com.maxcheung.futureclearer.model.FlatFileSpec;
import au.com.maxcheung.futureclearer.model.FutureTransaction;
import au.com.maxcheung.futureclearer.model.FutureTransactionDTO;
import au.com.maxcheung.futureclearer.transform.FutureTransactionTransformer;

public class FutureTransactionTransformerTest {

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

    @Before
    public void setup() throws Exception {
        transformer = new FutureTransactionTransformer();
        flatFileReader = new FlatFileReader(new CsvReader(FlatFileSpec.class));
        lineMapper = flatFileReader.getLineMapper(FILESPEC_FILEPATH + "future-filespec.csv");
        futureTransactionDTO = lineMapper.mapLine(TRANSACTION_ROW, 0);
    }

    @Test
    public void shouldTransformFutureTransaction() throws Exception  {
        FutureTransaction futureTransaction = transformer.transform(futureTransactionDTO);
        assertEquals(RECORD_CODE, futureTransaction.getRecordCode());
    }

    @Test
    public void shouldConvertClientDetails() throws Exception  {
        assertEquals(CLIENT_TYPE, futureTransactionDTO.getClientType());
        assertEquals(CLIENT_NUMBER, futureTransactionDTO.getClientNumber());
        assertEquals(ACCOUNT_NUMBER, futureTransactionDTO.getAccountNumber());
        assertEquals(SUB_ACCOUNT_NUMBER, futureTransactionDTO.getSubAccountNumber());
        FutureTransaction futureTransaction = transformer.transform(futureTransactionDTO);
        assertEquals(CLIENT_TYPE, futureTransaction.getClientType());
        assertEquals(CLIENT_NUMBER, futureTransaction.getClientNumber());
        assertEquals(ACCOUNT_NUMBER, futureTransaction.getAccountNumber());
        assertEquals(SUB_ACCOUNT_NUMBER, futureTransaction.getSubAccountNumber());
        
        String clientInfo = Arrays.asList(CLIENT_TYPE, CLIENT_NUMBER, ACCOUNT_NUMBER, SUB_ACCOUNT_NUMBER).stream().collect(Collectors.joining("_"));
        assertEquals( clientInfo , futureTransaction.getClientInfo());
    }

    @Test
    public void shouldConvertProductDetails() throws Exception  {
        assertEquals(PRODUCT_GROUP_CODE, futureTransactionDTO.getProductGroupCode());
        assertEquals(EXCHANGE_CODE, futureTransactionDTO.getExchangeCode());
        assertEquals(SYMBOL, futureTransactionDTO.getSymbol());
        assertEquals(EXPIRATION_DATE, futureTransactionDTO.getExpirationDate());
        FutureTransaction futureTransaction = transformer.transform(futureTransactionDTO);
        assertEquals(PRODUCT_GROUP_CODE, futureTransaction.getProductGroupCode());
        assertEquals(EXCHANGE_CODE, futureTransaction.getExchangeCode());
        assertEquals(SYMBOL, futureTransaction.getSymbol());
        assertEquals(EXPIRATION_DATE, futureTransaction.getExpirationDate());
        String productInfo = Arrays.asList(PRODUCT_GROUP_CODE, EXCHANGE_CODE, SYMBOL, EXPIRATION_DATE).stream().collect(Collectors.joining("_"));
        assertEquals( productInfo , futureTransaction.getProductInfo());
    }

    @Test
    public void shouldConvertTransactionAmounts() throws Exception  {
        assertEquals(QUANTITY_LONG, futureTransactionDTO.getQuantityLong());
        assertEquals(QUANTITY_SHORT, futureTransactionDTO.getQuantityShort());
        FutureTransaction futureTransaction = transformer.transform(futureTransactionDTO);
        assertEquals(QUANTITY_LONG, futureTransaction.getQuantityLong());
        assertEquals(QUANTITY_SHORT, futureTransaction.getQuantityShort());
    }

    @Test
    public void shouldConvertFees() throws Exception  {
        assertEquals(BROKER_FEE, futureTransactionDTO.getExchangeBrokerFee());
        assertEquals(CLEARING_FEE, futureTransactionDTO.getClearingFee());
        assertEquals(COMMISSION, futureTransactionDTO.getCommission());
        FutureTransaction futureTransaction = transformer.transform(futureTransactionDTO);
        assertEquals(EXCHANGE_BROKER_FEE_VALUE, futureTransaction.getExchangeBrokerFee());
        assertEquals(CLEARING_FEE_VALUE, futureTransaction.getClearingFee());
        assertEquals(COMMISION_VALUE, futureTransaction.getCommission());
    }
    
    @Test
    public void shouldConvertPrice() throws Exception  {
        assertEquals(TRANSACTION_PRICE, futureTransactionDTO.getTransactionPrice());
        FutureTransaction futureTransaction = transformer.transform(futureTransactionDTO);
        assertEquals(TRANSACTION_PRICE_VALUE, futureTransaction.getTransactionPrice());
    }

    @Test
    public void shouldNegateValues() throws Exception  {
        futureTransactionDTO.setQuantityLongSign("-");
        futureTransactionDTO.setQuantityLong(1L);
        futureTransactionDTO.setQuantityShortSign("-");
        futureTransactionDTO.setQuantityShort(1L);
        FutureTransaction futureTransaction = transformer.transform(futureTransactionDTO);
        assertEquals(Long.valueOf(-1L), futureTransaction.getQuantityLong());
        assertEquals(Long.valueOf(-1L), futureTransaction.getQuantityShort());
    }
}
