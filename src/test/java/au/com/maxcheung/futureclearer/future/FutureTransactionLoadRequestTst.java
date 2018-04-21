package au.com.maxcheung.futureclearer.future;

public class FutureTransactionLoadRequestTst {

    protected static final String REPORTFILE_NAME = "reportfileName";

    protected static final String SPECFILE_NAME = "specfileName";

    protected static final String DATAFILE_NAME = "datafileName";

    protected FutureTransactionLoadRequest loadRequest;

    protected FutureTransactionLoadRequest getRequest() {
        FutureTransactionLoadRequest lookupValue = new FutureTransactionLoadRequest();
        lookupValue.setDatafileName(DATAFILE_NAME);
        lookupValue.setSpecfileName(SPECFILE_NAME);
        lookupValue.setReportfileName(REPORTFILE_NAME);
        return lookupValue;
    }
}
