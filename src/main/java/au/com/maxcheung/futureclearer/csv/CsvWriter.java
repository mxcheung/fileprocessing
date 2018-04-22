package au.com.maxcheung.futureclearer.csv;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

/**
 * CSVWriter to export Report File.
 * 
 * @author Max Cheung <max.cheung@optusnet.com.au>
 */

public class CsvWriter extends BaseWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvWriter.class);

    private final CsvMapper mapper = new CsvMapper();
    private final CsvSchema schema;

    public CsvWriter(final Class<?> type) {

        schema = getSchema(type);
    }

    private CsvSchema getSchema(final Class<?> type) {
        CsvSchema csvSchema = mapper.schemaFor(type).withHeader();
        csvSchema = csvSchema.withColumnSeparator(',');
        return csvSchema;
    }

    public <T> List<T> write(List<T> rows, String filename) throws FileNotFoundException, IOException {
        return write(rows, getOutPutStreamWriter(filename));
    }

    public <T> List<T> write(List<T> rows, Writer writer) throws FileNotFoundException, IOException {
        ObjectWriter myObjectWriter = mapper.writer(schema);
        LOGGER.info("Writing rows : {}", rows.size());

        myObjectWriter.writeValue(writer, rows);
        return rows;

    }

}
