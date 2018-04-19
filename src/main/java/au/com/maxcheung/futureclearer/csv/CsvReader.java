package au.com.maxcheung.futureclearer.csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

/**
 * CSVReader to load Questionnaire Definition File.
 * 
 * @author Max Cheung <max.cheung@lonsec.com.au>
 */

public class CsvReader {

    private final CsvMapper mapper = new CsvMapper();
    private final CsvSchema schema;
    private final Class<?> type;

    public CsvReader(final Class<?> type) {
        this.type = type;
        schema = getSchema(type);
    }

    private CsvSchema getSchema(final Class<?> pojoType) {
        CsvSchema csvSchema = mapper.schemaFor(pojoType);
        csvSchema = csvSchema.withColumnSeparator(',');
        return csvSchema;
    }

    public <T> List<T> readCsv(String filename) throws FileNotFoundException, IOException {
        InputStream inputStream = new FileInputStream(getFile(filename));
        return readCsv(inputStream);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> readCsv(InputStream inputStream) throws FileNotFoundException, IOException {
        ArrayList<T> rows = new ArrayList<T>();
        Reader reader = getReader(inputStream);
        ObjectReader oReader = mapper.readerFor(type).with(schema);
        MappingIterator<?> mi = oReader.readValues(reader);
        while (mi.hasNext()) {
            rows.add((T) mi.next());
        }
        return rows;
    }

    public Reader getReader(InputStream inputStream) throws FileNotFoundException {
        Reader reader = new InputStreamReader(inputStream);
        return reader;
    }

    public File getFile(String fileName) {
        return new File(fileName);
    }

}
