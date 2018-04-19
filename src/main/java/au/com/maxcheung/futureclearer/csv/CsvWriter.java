package au.com.maxcheung.futureclearer.csv;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

/**
 * CSVWriter to export Report File.
 * 
 * @author Max Cheung <max.cheung@optusnet.com.au>
 */

public class CsvWriter extends BaseWriter {

    private final CsvMapper mapper = new CsvMapper();
    private final CsvSchema schema;

    public CsvWriter(final Class<?> type) {

        schema = getSchema(type);
    }

    private CsvSchema getSchema(final Class<?> type) {
        CsvSchema csvSchema = mapper.schemaFor(type);
        csvSchema = csvSchema.withColumnSeparator(',');
        return csvSchema;
    }

    public <T> void write(List<T> rows, String filename) throws FileNotFoundException, IOException {
        write(rows, getOutPutStreamWriter(filename));
    }

    public <T> void write(List<T> rows, Writer writer) throws FileNotFoundException, IOException {
        ObjectWriter myObjectWriter = mapper.writer(schema);
        myObjectWriter.writeValue(writer, rows);

    }

}
