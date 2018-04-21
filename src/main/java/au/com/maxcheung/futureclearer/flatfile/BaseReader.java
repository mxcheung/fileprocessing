package au.com.maxcheung.futureclearer.flatfile;

import org.springframework.batch.item.file.mapping.DefaultLineMapper;

import au.com.maxcheung.futureclearer.model.FutureTransaction;

public class BaseReader {

    private DefaultLineMapper<FutureTransaction> lineMapper;

}
