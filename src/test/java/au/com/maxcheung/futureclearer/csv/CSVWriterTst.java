package au.com.maxcheung.futureclearer.csv;

public abstract class CSVWriterTst {

    private static final String SR_ARFI_FILEPATH = "src\\test\\resources\\arfi\\SR\\arfi-20170619";

    private CsvWriter genericCSVWriter;

    public static String getSrArfiFilepath() {
        return SR_ARFI_FILEPATH;
    }

    public CsvWriter getGenericCSVWriter() {
        return genericCSVWriter;
    }
    
    

}
