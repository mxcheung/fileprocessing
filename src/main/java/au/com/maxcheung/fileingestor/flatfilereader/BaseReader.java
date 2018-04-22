package au.com.maxcheung.fileingestor.flatfilereader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;

import au.com.maxcheung.fileingestor.future.model.FutureTransaction;

public abstract class BaseReader {

    protected ItemReader<FutureTransaction> getReader(LineMapper<FutureTransaction> futureLineMapper) {
        FlatFileItemReader<FutureTransaction> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setLineMapper(futureLineMapper);
        return flatFileItemReader;
    }

    protected BeanWrapperFieldSetMapper<FutureTransaction> getFieldSetMapper() {
        BeanWrapperFieldSetMapper<FutureTransaction> wrapper = new BeanWrapperFieldSetMapper<FutureTransaction>();
        wrapper.setTargetType(FutureTransaction.class);
        return wrapper;
    }

    @SuppressWarnings("unchecked")
    protected <T> List<T> readData(FlatFileItemReader<FutureTransaction> reader) throws Exception {
        List<T> txns = new ArrayList<>();
        T t = null;
        do {
            t = (T) reader.read();
            if (t != null) {
                txns.add(t);
            }
        } while (t != null);
        return txns;

    }

    protected File getFile(String fileName) {
        return new File(fileName);
    }

}
