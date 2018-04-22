package au.com.maxcheung.futureclearer.flatfile;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.core.io.ClassPathResource;

import au.com.maxcheung.futureclearer.model.FutureTransaction;

public class FileTokenizerTest {

    private static final int FIELD_COUNT = 34;

    private static final String TRANSACTION_DATE = "transactionDate";

    private static final String LINE = "315CL  432100020001SGXDC FUSGX NK    20100910JPY01B 0000000001 "
            + "0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012380     688032000092500000000             O";

    private FlatFileReaderImpl flatFileReader;
    private FixedLengthTokenizer tokenizer;

    @Before
    public void setup() throws IOException {
        flatFileReader = new FlatFileReaderImpl();
        InputStream cpfileSpec = new ClassPathResource("future-filespec.csv").getInputStream();
        tokenizer = flatFileReader.getTokenizer(cpfileSpec);
    }

    @Test
    public void shouldTokenize() throws IOException {
        FieldSet fields = tokenizer.tokenize(LINE);
        assertEquals(FIELD_COUNT, fields.getFieldCount());
        Properties props = fields.getProperties();
        assertEquals("20100820", props.getProperty(TRANSACTION_DATE));
    }

    @Test
    public void shouldMapLine() throws Exception {
        DefaultLineMapper<FutureTransaction> lineMapper = getMapper();
        FutureTransaction ticket = lineMapper.mapLine(LINE, 0);
        assertEquals("CL", ticket.getClientType());
        assertEquals(Long.valueOf(1), ticket.getQuantityLong());
    }

    private DefaultLineMapper<FutureTransaction> getMapper() {
        DefaultLineMapper<FutureTransaction> lineMapper = new DefaultLineMapper<FutureTransaction>();
        BeanWrapperFieldSetMapper<FutureTransaction> wrapper = new BeanWrapperFieldSetMapper<FutureTransaction>();
        wrapper.setTargetType(FutureTransaction.class);
        lineMapper.setFieldSetMapper(wrapper);
        lineMapper.setLineTokenizer(tokenizer);
        return lineMapper;
    }

}
