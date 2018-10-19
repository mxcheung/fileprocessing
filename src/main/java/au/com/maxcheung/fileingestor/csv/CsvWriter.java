package au.com.maxcheung.fileingestor.csv;

import static au.com.maxcheung.fileingestor.utils.LogUtils.futureLogMsg;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

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

    public static final String USE_HEADER = "USE_HEADER";

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvWriter.class);

    private final CsvMapper mapper = new CsvMapper();
    private final CsvSchema schema;

    public CsvWriter(final Class<?> type, final Map<String, Object> options) {
        schema = getSchema(type, options);
    }

    private CsvSchema getSchema(final Class<?> type, final Map<String, Object> options) {
        CsvSchema csvSchema = mapper.schemaFor(type).withHeader();
        csvSchema = csvSchema.withUseHeader((boolean) options.getOrDefault(USE_HEADER, true));
        csvSchema = csvSchema.withColumnSeparator(',');
        return csvSchema;
    }

    public <T> List<T> write(List<T> rows, String filename) throws FileNotFoundException, IOException {
        return write(rows, getOutPutStreamWriter(filename));
    }

    public <T> List<T> write(List<T> rows, Writer writer) throws FileNotFoundException, IOException {
        ObjectWriter myObjectWriter = mapper.writer(schema);
        LOGGER.info(futureLogMsg("Writing rows : {}"), rows.size());

        myObjectWriter.writeValue(writer, rows);
        return rows;

    }

}
