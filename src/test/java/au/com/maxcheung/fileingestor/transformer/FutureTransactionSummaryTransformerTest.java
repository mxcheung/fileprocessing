package au.com.maxcheung.fileingestor.transformer;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;

import au.com.maxcheung.fileingestor.flatfilereader.FlatFileReaderImpl;
import au.com.maxcheung.fileingestor.future.model.FutureTransaction;
import au.com.maxcheung.fileingestor.future.model.FutureTransactionSummary;

public class FutureTransactionSummaryTransformerTest {

    protected static final String FILESPEC_FILEPATH = "src\\test\\resources\\filespec\\";

    private static final String EXPIRATION_DATE = "20100910";

    private static final String SYMBOL = "NK";

    private static final String EXCHANGE_CODE = "SGX";

    private static final String PRODUCT_GROUP_CODE = "FU";

    private static final String SUB_ACCOUNT_NUMBER = "0001";

    private static final String ACCOUNT_NUMBER = "0002";

    private static final String CLIENT_NUMBER = "4321";

    private static final String CLIENT_TYPE = "CL";

    private static final String TRANSACTION_ROW1 = "315CL  432100020001SGXDC FUSGX NK    20100910JPY01B 0000000001 "
            + "0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012380     688032000092500000000             O";
    private static final String TRANSACTION_ROW2 = "315CL  123400020001SGXDC FUSGX NK    20100910JPY01B 0000000001 0"
            + "000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012380     688032000092500000000             O";

    private DefaultLineMapper<FutureTransaction> lineMapper;
    private FutureTransaction futureTransactionDTO;
    private FutureTransformer futureTransactionSummaryTransformer;

    @Before
    public void setUp() throws Exception {
        futureTransactionSummaryTransformer = new FutureTransformerImpl();
        // flatFileReader = new FlatFileReader(new CsvReader(FlatFileSpec.class));
        FlatFileReaderImpl flatFileReader = new FlatFileReaderImpl();
        lineMapper = flatFileReader.getLineMapper();
        futureTransactionDTO = lineMapper.mapLine(TRANSACTION_ROW1, 0);
    }

    @Test
    public void shouldTransformFutureTransaction() throws Exception {
        FutureTransaction futureTransaction1 = futureTransactionDTO;
        FutureTransaction futureTransaction2 = futureTransactionDTO;
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
    public void shouldTransformMultipleAccounts() throws Exception {
        List<FutureTransaction> txns = new ArrayList<FutureTransaction>();
        FutureTransaction futureTransaction1 = lineMapper.mapLine(TRANSACTION_ROW1, 0);
        FutureTransaction futureTransaction2 = lineMapper.mapLine(TRANSACTION_ROW2, 0);
        futureTransaction2.setQuantityLong(null);
        assertEquals(BigDecimal.ZERO, futureTransaction2.getTotalTransactionAmount());
        txns.add(futureTransaction1);
        txns.add(futureTransaction2);
        List<FutureTransactionSummary> result = futureTransactionSummaryTransformer.transform(txns);
        assertEquals(2, result.size());
    }

}
