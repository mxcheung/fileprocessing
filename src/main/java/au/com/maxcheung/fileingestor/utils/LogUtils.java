package au.com.maxcheung.fileingestor.utils;

public final class LogUtils {

    private static final String FUTURE_LOG_BASE = "[-FUTURE-]";

    private LogUtils() {
        super();
    }
    
    public static String futureLogMsg(String message) {
        return FUTURE_LOG_BASE + message;  
    }
    
    
}
